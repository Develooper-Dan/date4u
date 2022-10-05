package com.tutego.date4u.core.interfaces.controller;

import com.tutego.date4u.core.photo.Photo;
import com.tutego.date4u.core.profile.Profile;
import com.tutego.date4u.core.profile.ProfileFormData;
import com.tutego.date4u.core.profile.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class Date4uWebController {

    private final ProfileRepository profiles;
    private final Logger log = LoggerFactory.getLogger( getClass() );
    public Date4uWebController(ProfileRepository profiles ) {
        this.profiles = profiles;
    }

    @RequestMapping( "/*" )
    public String indexPage(Model model) {
        model.addAttribute("totalProfiles", profiles.count());
        return "index";
    }

    @RequestMapping( "/profile" )
    public String profilePage() {
        return "profile";
    }

    @RequestMapping( "/profile/{id}" )
    public String profilePage(@PathVariable long id, Model model) {
        Optional<Profile> maybeProfile = profiles.findById(id);
        if(maybeProfile.isEmpty()) return "redirect:/";
        Profile profile = maybeProfile.get();
        model.addAttribute("profile", new ProfileFormData(
                profile.getId(), profile.getNickname(), profile.getBirthdate(),
                profile.getHornlength(), profile.getGender(),
                profile.getAttractedToGender(), profile.getDescription(),
                profile.getLastseen(),
                profile.getPhotos().stream().map( Photo::getName ).toList()
        ));
        return "profile";
    }

    @RequestMapping( "/search" )
    public String searchPage(Model model) {
        model.addAttribute("profiles", profiles.findAll());
        return "search";
    }

    @PostMapping( "/save" )
    public String saveProfile( @ModelAttribute ProfileFormData profile ) {
        log.info(profile.toString());
        return "redirect:/profile/" + profile.getId();
    }

}
