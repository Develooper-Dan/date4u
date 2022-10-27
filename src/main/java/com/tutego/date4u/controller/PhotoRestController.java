package com.tutego.date4u.controller;

import com.tutego.date4u.services.PhotoService;
import com.tutego.date4u.repositories.ProfileRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
@CrossOrigin
@RestController
public class PhotoRestController {

    private final PhotoService photoService;
    private final ProfileRepository profiles;

    public PhotoRestController( PhotoService photoService, ProfileRepository profiles ) {
        this.photoService = photoService;
        this.profiles = profiles;
    }

    @ExceptionHandler( )
    public ResponseEntity<String> noSuchPhoto(NoSuchElementException e) {
        return ResponseEntity.status( HttpStatus.NOT_FOUND )
                             .body(null);
    }

    @GetMapping( path = "/api/photos/{imagename}" )
    public ResponseEntity<String> bas64Photo(@PathVariable String imagename) {
        byte[] photo = photoService.download( imagename ).orElseThrow();
        String base64Photo = Base64Utils.encodeToString(photo);
        return ResponseEntity.ok(base64Photo);
    }

}
