package com.tutego.date4u.interfaces.controller;

import com.tutego.date4u.photo.Photo;
import com.tutego.date4u.photo.PhotoService;
import com.tutego.date4u.profile.Profile;
import com.tutego.date4u.profile.ProfileFormData;
import com.tutego.date4u.profile.ProfileRepository;
import com.tutego.date4u.profile.UnicornRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/api/login")
public class LoginRestController {

    private final ProfileRepository profiles;
    private final Logger log = LoggerFactory.getLogger( getClass() );

    public LoginRestController(ProfileRepository profiles ) {
        this.profiles = profiles;
    }

    @PostMapping
    public ResponseEntity<?> userData(Principal principal) {
        return ResponseEntity.ok(principal);
    }


}
