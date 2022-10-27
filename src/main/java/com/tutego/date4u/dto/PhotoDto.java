package com.tutego.date4u.dto;

public class PhotoDto {
    private final String name;
    private final boolean isProfilePhoto;

    public PhotoDto(String name, boolean isProfilePhoto) {
        this.name = name;
        this.isProfilePhoto = isProfilePhoto;
    }

    public String getName() {
        return name;
    }

    public boolean isProfilePhoto() {
        return isProfilePhoto;
    }

}
