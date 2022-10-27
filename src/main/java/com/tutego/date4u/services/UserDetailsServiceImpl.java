package com.tutego.date4u.services;

import com.tutego.date4u.entities.Unicorn;
import com.tutego.date4u.repositories.UnicornRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

@Configuration
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UnicornRepository unicornRepository;
    @Override
    public UserDetails loadUserByUsername(String username ) throws UsernameNotFoundException {
        Optional<Unicorn> unicorn = unicornRepository.findUnicornByEmail( username );
        if( unicorn.isEmpty() ) {
            throw new UsernameNotFoundException( "User not found " + username );
        }
        return new org.springframework.security.core.userdetails.User(
                unicorn.get().getEmail(),
                unicorn.get().getPassword(),
                Collections.emptyList() );
    }
}
