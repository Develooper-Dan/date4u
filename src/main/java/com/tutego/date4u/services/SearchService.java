package com.tutego.date4u.services;

import com.tutego.date4u.dto.PhotoDto;
import com.tutego.date4u.dto.SearchFormData;
import com.tutego.date4u.dto.SearchProfileDto;
import com.tutego.date4u.entities.Photo;
import com.tutego.date4u.entities.Profile;
import com.tutego.date4u.repositories.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@Validated
public class SearchService {

    private ProfileRepository profiles;

    public SearchService(ProfileRepository profiles){
        this.profiles = profiles;
    }

    public SearchProfileDto returnSearchProfileDto(Profile profile) {
        int age = (int) ChronoUnit.YEARS.between(profile.getBirthdate(), LocalDate.now());
        PhotoDto profilePhotoDto = null;
        Optional<Photo> maybeProfilePhoto = profile.getPhotos().stream()
                                                   .filter(Photo::isProfilePhoto)
                                                   .findFirst();

        if (maybeProfilePhoto.isPresent()) {
            Photo profilePhoto = maybeProfilePhoto.get();
            profilePhotoDto = new PhotoDto(profilePhoto.getName(), profilePhoto.isProfilePhoto());
        }

        return new SearchProfileDto(
                profile.getId(), profile.getNickname(), profile.getHornlength(), profile.getGender(),
                age, profilePhotoDto);
    }

    public Page<SearchProfileDto> performSearch(SearchFormData searchFormData, Pageable pageable ){
        String nickname = searchFormData.getNickname();
        int minHornlength = searchFormData.getMinHornlength();
        int maxHornlength = searchFormData.getMaxHornlength();
        int minAge = searchFormData.getMinAge();
        int maxAge = searchFormData.getMaxAge();
        Byte gender = searchFormData.getGender();
        Integer attractedToGender = searchFormData.getAttractedToGender();
        LocalDate now = LocalDate.now();
        LocalDate minAgeAsDate = LocalDate.of(now.getYear() - minAge, now.getMonthValue(), now.getDayOfMonth());
        LocalDate maxAgeAsDate = LocalDate.of(now.getYear() - maxAge, now.getMonthValue(), now.getDayOfMonth());
        Page<Profile> foundProfiles =  profiles.findAllProfilesBySearchParams(nickname, minHornlength, maxHornlength, minAgeAsDate, maxAgeAsDate, gender, attractedToGender, pageable);
        return foundProfiles.map(this::returnSearchProfileDto);
    }
}
