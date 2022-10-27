package com.tutego.date4u.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String jwtSecret;

    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                  .withSubject("User Details")
                  .withClaim("email", email)
                  .withIssuedAt(new Date())
                  .withIssuer("Date4u")
                  .sign(Algorithm.HMAC256(jwtSecret));

    }

    public String retrieveTokenFromAuthHeader(String authHeader) {
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret))
                                  .withSubject("User Details")
                                  .withIssuer("Date4u")
                                  .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }

}