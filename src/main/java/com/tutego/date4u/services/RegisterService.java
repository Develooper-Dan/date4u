package com.tutego.date4u.services;

import com.tutego.date4u.dto.RegisterData;
import com.tutego.date4u.entities.Profile;
import com.tutego.date4u.entities.Unicorn;
import com.tutego.date4u.repositories.ProfileRepository;
import com.tutego.date4u.repositories.UnicornRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Validated
public class RegisterService {

    private ProfileRepository profiles;
    private UnicornRepository unicorns;

    public RegisterService(ProfileRepository profiles, UnicornRepository unicorns){

        this.profiles = profiles;
        this.unicorns = unicorns;
    }
    @Nullable
    public Map<String, String> checkEmailAndNickname(String email, String nickname) {
        Map<String, String> errorMessages = new HashMap<>();
        if (unicorns.findUnicornByEmail(email).isPresent()) {
            errorMessages.put("email", "Email is already in use");
        }
        if (profiles.findProfileByNickname(nickname).isPresent()) {
            errorMessages.put("nickname", "Nickname already taken!");
        }
        return errorMessages.isEmpty() ? null : errorMessages;
    }
    public Unicorn register(RegisterData registerData){
        String email = registerData.getEmail();
        String password = registerData.getPassword();
        String nickname = registerData.getNickname();
        String description = registerData.getDescription();
        Integer attractedToGender = registerData.getAttractedToGender();
        LocalDate birthdate = registerData.getBirthdate();
        LocalDateTime lastseen = LocalDateTime.now().truncatedTo(TimeUnit.SECONDS.toChronoUnit() );
        int gender = registerData.getGender();
        int hornlength = registerData.getHornlength();
        Profile newProfile = new Profile(nickname, birthdate, hornlength,
            gender, attractedToGender, description,lastseen);
        profiles.saveAndFlush(newProfile);
        Unicorn newUnicorn = new Unicorn(email, "{noop}" + password, newProfile);
        unicorns.save(newUnicorn);
        return newUnicorn;
    }
}
