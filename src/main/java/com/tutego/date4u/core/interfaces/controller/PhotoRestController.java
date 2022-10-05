package com.tutego.date4u.core.interfaces.controller;

import com.tutego.date4u.core.photo.Photo;
import com.tutego.date4u.core.photo.PhotoService;
import com.tutego.date4u.core.profile.Profile;
import com.tutego.date4u.core.profile.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@RestController
public class PhotoRestController {

    private final PhotoService photos;
    private final ProfileRepository profiles;

    public PhotoRestController( PhotoService photos, ProfileRepository profiles ) {
        this.photos = photos;
        this.profiles = profiles;
    }

    @ExceptionHandler( )
    public ResponseEntity<String> noSuchPhoto(NoSuchElementException e) {
        return ResponseEntity.status( HttpStatus.NOT_FOUND )
                             .body(null);
    }

    @GetMapping( path = "/api/photos/{imagename}",
            produces = MediaType.IMAGE_JPEG_VALUE )
    public byte[] photo(@PathVariable String imagename) {
        return photos.download( imagename ).orElseThrow();
    }

}
