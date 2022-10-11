package com.tutego.date4u.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceConfiguration();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig ) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService( userDetailsService() );
        return authProvider;
    }
    @Bean
    public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception {

        http.authenticationProvider( authenticationProvider() );
        return http.httpBasic()
                   .and()
                   .authorizeRequests().antMatchers("/signup", "api/login").permitAll()
                   .anyRequest().authenticated()
                   .and()
                   .formLogin().disable()
                   .csrf().disable()
                   .build();
    }
}
