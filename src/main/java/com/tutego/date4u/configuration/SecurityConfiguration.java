package com.tutego.date4u.configuration;

import com.tutego.date4u.jwt.JWTFilter;
import com.tutego.date4u.services.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
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
    public SecurityFilterChain filterChain( HttpSecurity http, @Autowired JWTFilter filter ) throws Exception {
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider( authenticationProvider() );
        return http.httpBasic().disable()
                   .formLogin().disable()
                   .csrf().disable()
                   .cors()
                   .and()
                   .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                   .anyRequest().authenticated()
                   .and()
                   .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                   .and()
                   .exceptionHandling()
                   .authenticationEntryPoint(
                           (request, response, authException) ->
                                   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage())
                   )
                   .and()
                   .build();
    }
}
