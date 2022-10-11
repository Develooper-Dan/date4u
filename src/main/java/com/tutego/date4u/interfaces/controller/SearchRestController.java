package com.tutego.date4u.interfaces.controller;

import com.tutego.date4u.profile.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class SearchRestController {

    private final ProfileRepository profiles;
    private final Logger log = LoggerFactory.getLogger( getClass() );
    public SearchRestController(ProfileRepository profiles ) {
        this.profiles = profiles;
    }

    @RequestMapping( "/search" )
    public String searchPage(Model model) {
        model.addAttribute("profiles", profiles.findAll());
        return "search";
    }



}
