package com.tutego.date4u.interfaces.controller;

import com.tutego.date4u.configuration.UserDetailsServiceConfiguration;
import com.tutego.date4u.jwt.JWTUtil;
import com.tutego.date4u.profile.Unicorn;
import com.tutego.date4u.profile.UnicornDto;
import com.tutego.date4u.profile.UnicornRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class LoginRestController {

    private final UnicornRepository unicorns;
    private JWTUtil jwtUtil;
    private UserDetailsServiceConfiguration userDetailsService;
    private AuthenticationManager authManager;
    private final Logger log = LoggerFactory.getLogger( getClass() );

    public LoginRestController(UnicornRepository unicorns, JWTUtil jwtUtil, UserDetailsServiceConfiguration userDetailsService, AuthenticationManager authManager) {
        this.unicorns = unicorns;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authManager = authManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> userData(@RequestBody UnicornDto unicornData) {
            String email = unicornData.getEmail();
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(email, unicornData.getPassword());
            Authentication auth = authManager.authenticate(authInputToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            Unicorn unicorn = unicorns.findUnicornByEmail(email).get();
            unicornData.setId(unicorn.getId());
            unicornData.setProfileID(unicorn.getProfile().getId());
            unicornData.setJwt(jwtUtil.generateToken(email));
            return ResponseEntity.ok(unicornData);
    }


}
