package com.tutego.date4u.controller;

import com.tutego.date4u.dto.JWTDto;
import com.tutego.date4u.dto.LoginData;
import com.tutego.date4u.dto.RegisterData;
import com.tutego.date4u.entities.Unicorn;
import com.tutego.date4u.jwt.JWTUtil;
import com.tutego.date4u.repositories.ProfileRepository;
import com.tutego.date4u.repositories.UnicornRepository;
import com.tutego.date4u.services.RegisterService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final RegisterService registerService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final Logger log = LoggerFactory.getLogger( getClass() );

    public AuthRestController( RegisterService registerService, JWTUtil jwtUtil, AuthenticationManager authManager) {
        this.registerService = registerService;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    @PostMapping( "/signup")
    public ResponseEntity<?> signUpHandler(@Valid @RequestBody RegisterData registerData){
        String email = registerData.getEmail();
        String nickname = registerData.getNickname();
        Map<String, String> errors = registerService.checkEmailAndNickname(email, nickname);
        if(errors != null){
            return ResponseEntity.badRequest().body(errors);
        }
        Unicorn newUnicorn = registerService.register(registerData);
        String jwt = jwtUtil.generateToken(email);
        return ResponseEntity.created(URI.create("/api/profiles/" + newUnicorn.getProfile().getId())).body(new JWTDto( jwt ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginHandler(@Valid @RequestBody LoginData loginData) {
            String email = loginData.getEmail();
            String password = loginData.getPassword();

            UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(email, password);
            Authentication auth = authManager.authenticate(authInputToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwt = jwtUtil.generateToken(email);
            return ResponseEntity.ok( new JWTDto( jwt ));

    }


}
