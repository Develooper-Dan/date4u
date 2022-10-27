package com.tutego.date4u.controller;

import com.tutego.date4u.dto.UnicornDto;
import com.tutego.date4u.entities.Unicorn;
import com.tutego.date4u.jwt.JWTUtil;
import com.tutego.date4u.repositories.UnicornRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@CrossOrigin
@RestController
@RequestMapping("/api/unicorn")
public class UnicornRestController {

    private final UnicornRepository unicorns;
    private JWTUtil jwtUtil;
    private final Logger log = LoggerFactory.getLogger( getClass() );

    public UnicornRestController(UnicornRepository unicorns, JWTUtil jwtUtil) {
        this.unicorns = unicorns;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping(  )
    public ResponseEntity<?> authenticatedUnicorn(HttpServletRequest request) {
        String jwt = jwtUtil.retrieveTokenFromAuthHeader(request.getHeader("Authorization"));
        String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);
        if(email != null) {
            Unicorn unicorn = unicorns.findUnicornByEmail(email).get();
            long id = unicorn.getId();
            long profileID = unicorn.getProfile().getId();
            return ResponseEntity.ok(new UnicornDto(id, email, profileID));
        }
        return ResponseEntity.status(401).build();
    }


}
