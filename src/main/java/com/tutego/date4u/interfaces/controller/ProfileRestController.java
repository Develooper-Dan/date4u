package com.tutego.date4u.interfaces.controller;

import com.tutego.date4u.photo.Photo;
import com.tutego.date4u.photo.PhotoService;
import com.tutego.date4u.profile.Profile;
import com.tutego.date4u.profile.ProfileFormData;
import com.tutego.date4u.profile.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/api/profiles/")
public class ProfileRestController {

    private final PhotoService photos;
    private final ProfileRepository profiles;
    private final Logger log = LoggerFactory.getLogger( getClass() );

    public ProfileRestController(PhotoService photos, ProfileRepository profiles ) {
        this.photos = photos;
        this.profiles = profiles;
    }

    @GetMapping( "{id}" )
    public ResponseEntity<?> profilePage(@PathVariable long id) {
        Optional<Profile> maybeProfile = profiles.findById(id);
        if(maybeProfile.isEmpty()) return ResponseEntity.notFound().build();
        Profile profile = maybeProfile.get();
        return ResponseEntity.ok(new ProfileFormData(
                profile.getId(), profile.getNickname(), profile.getBirthdate(),
                profile.getHornlength(), profile.getGender(),
                profile.getAttractedToGender(), profile.getDescription(),
                profile.getLastseen(),
                profile.getPhotos().stream().map( Photo::getName ).toList()));
    }

    @PostMapping( "{id}/photos/" )
    public ResponseEntity<?> saveImage(@PathVariable()  long id, @RequestParam() MultipartFile file) throws IOException {
        Optional<Profile> maybeProfile = profiles.findById(id);
        if( maybeProfile.isEmpty() )
            return ResponseEntity.notFound().build();
        Profile profile = maybeProfile.get();
        String imageName = photos.upload(file.getBytes());
        Photo photo = new Photo( null, profile, imageName, false,
                                    LocalDateTime.now().truncatedTo(TimeUnit.SECONDS.toChronoUnit() ) );
        profile.add(photo);
        profiles.save(profile);
        return ResponseEntity.created( URI.create("/api/photos/" + imageName)).build();
    }

    @PostMapping( "{id}/save" )
    public ResponseEntity<?> saveProfile( @RequestBody ProfileFormData profileUpdate ) {
        log.info(profileUpdate.toString());
        Optional<Profile> maybeProfile = profiles.findById(profileUpdate.getId());
        if( maybeProfile.isEmpty() ){
            return ResponseEntity.notFound().build();
        }
        Profile profile = maybeProfile.get();
        profile.setNickname(profileUpdate.getNickname());
        profile.setBirthdate(profileUpdate.getBirthdate());
        profile.setGender(profileUpdate.getGender());
        profile.setAttractedToGender(profileUpdate.getAttractedToGender());
        profile.setHornlength(profileUpdate.getHornlength());
        profile.setDescription(profileUpdate.getDescription());
        profile.setLastseen(LocalDateTime.now().truncatedTo(TimeUnit.SECONDS.toChronoUnit() ));
        profiles.saveAndFlush(profile);
        return ResponseEntity.ok().build();
    }

}
