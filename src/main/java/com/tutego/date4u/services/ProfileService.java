package com.tutego.date4u.services;

import com.tutego.date4u.dto.PhotoDto;
import com.tutego.date4u.dto.ProfileDto;
import com.tutego.date4u.entities.Photo;
import com.tutego.date4u.entities.Profile;
import com.tutego.date4u.repositories.ProfileRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Validated
public class ProfileService {

    private ProfileRepository profiles;

    public ProfileService(ProfileRepository profiles){
        this.profiles = profiles;
    }

    public ProfileDto returnProfileDto(Profile profile){
        return new ProfileDto(
                profile.getId(), profile.getNickname(), profile.getBirthdate(),
                profile.getHornlength(), profile.getGender(),
                profile.getAttractedToGender(), profile.getDescription(),
                profile.getLastseen(),
                profile.getPhotos().stream().map( (Photo photo) -> new PhotoDto(photo.getName(), photo.isProfilePhoto())).toList());
    }


    @Nullable
    public Map<String, String> checkNickname(String nickname) {
        Map<String, String> errorMessage = null;
        if (profiles.findProfileByNickname(nickname).isPresent()) {
            errorMessage = Collections.singletonMap("nickname", "Nickname already taken!");
        }
        return errorMessage;
    }

    public void addPhotoToProfile(String imageName, Profile profile) {
        boolean setToProfilePhoto = profile.getPhotos().isEmpty();
        Photo photo = new Photo( null, profile, imageName, setToProfilePhoto,
                LocalDateTime.now().truncatedTo(TimeUnit.SECONDS.toChronoUnit() ) );
        profile.add(photo);
        profiles.save(profile);
    }

    public void update(Profile profile, ProfileDto profileUpdate ){
        profile.setNickname(profileUpdate.getNickname());
        profile.setBirthdate(profileUpdate.getBirthdate());
        profile.setGender(profileUpdate.getGender());
        profile.setAttractedToGender(profileUpdate.getAttractedToGender());
        profile.setHornlength(profileUpdate.getHornlength());
        profile.setDescription(profileUpdate.getDescription());
        profile.setLastseen(LocalDateTime.now().truncatedTo(TimeUnit.SECONDS.toChronoUnit() ));
        profiles.save(profile);
    }
}
