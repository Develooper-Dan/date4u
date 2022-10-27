package com.tutego.date4u.controller;

import com.tutego.date4u.dto.ProfileDto;
import com.tutego.date4u.dto.SearchFormData;
import com.tutego.date4u.dto.SearchProfileDto;
import com.tutego.date4u.repositories.ProfileRepository;
import com.tutego.date4u.services.ProfileService;
import com.tutego.date4u.services.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class SearchRestController {

    private final ProfileRepository profiles;
    private ProfileService profileService;
    private SearchService searchService;
    private final Logger log = LoggerFactory.getLogger( getClass() );
    public SearchRestController(ProfileRepository profiles, ProfileService profileService, SearchService searchService) {
        this.profiles = profiles;
        this.profileService = profileService;
        this.searchService = searchService;
    }

    @PostMapping( "/api/search" )
    public ResponseEntity<?> search(@RequestBody SearchFormData searchFormData,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "6") int pageSize) {
        Pageable pageable= PageRequest.of(page, pageSize);
        Page<SearchProfileDto> foundProfiles =  searchService.performSearch(searchFormData, pageable);
        if (foundProfiles.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

            return ResponseEntity.ok(foundProfiles);
    }



}
