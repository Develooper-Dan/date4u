package com.tutego.date4u.controller;

import com.tutego.date4u.services.PhotoService;
import com.tutego.date4u.entities.Profile;
import com.tutego.date4u.dto.ProfileDto;
import com.tutego.date4u.repositories.ProfileRepository;
import com.tutego.date4u.services.ProfileService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/profiles/")
public class ProfileRestController {

    private final PhotoService photoService;
    private ProfileService profileService;
    private final ProfileRepository profiles;
    private final Logger log = LoggerFactory.getLogger( getClass() );

    public ProfileRestController(PhotoService photoService, ProfileService profileService, ProfileRepository profiles ) {
        this.photoService = photoService;
        this.profileService = profileService;
        this.profiles = profiles;
    }

    @GetMapping( "{id}" )
    public ResponseEntity<?> profilePage(@PathVariable long id) {
        Optional<Profile> maybeProfile = profiles.findById(id);
        if(maybeProfile.isEmpty()) return ResponseEntity.notFound().build();
        Profile profile = maybeProfile.get();
        return ResponseEntity.ok(profileService.returnProfileDto(profile));
    }


    @PostMapping( value="{id}/photos", consumes= MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<?> saveImage(@PathVariable long id, @RequestBody MultipartFile file) throws IOException {
        Optional<Profile> maybeProfile = profiles.findById(id);
        if( maybeProfile.isEmpty() )
            return ResponseEntity.notFound().build();
        Profile profile = maybeProfile.get();
        String imageName = photoService.upload(file.getBytes());
        profileService.addPhotoToProfile(imageName, profile);
        return ResponseEntity.created( URI.create("/api/photos/" + imageName)).build();
    }

    @PostMapping( "{id}/save" )
    public ResponseEntity<?> saveProfile(@Valid @RequestBody ProfileDto profileUpdate ) {
        Optional<Profile> maybeProfile = profiles.findById(profileUpdate.getId());
        if( maybeProfile.isEmpty() ){
            return ResponseEntity.notFound().build();
        }
        Profile profile = maybeProfile.get();

        if( !profileUpdate.getNickname().equals(profile.getNickname()) ){
            Map<String, String> error = profileService.checkNickname(profileUpdate.getNickname());
            if( error != null ){
                return ResponseEntity.badRequest().body(error);
            }
        }

        profileService.update(profile, profileUpdate);
        return ResponseEntity.ok().build();
    }

}
