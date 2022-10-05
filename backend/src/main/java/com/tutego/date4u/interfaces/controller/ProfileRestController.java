package com.tutego.date4u.interfaces.controller;

import com.tutego.date4u.photo.Photo;
import com.tutego.date4u.photo.PhotoService;
import com.tutego.date4u.profile.Profile;
import com.tutego.date4u.profile.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

    @GetMapping
    public List<Profile> profiles() {
        return profiles.findAll();
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

}
