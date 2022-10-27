package com.tutego.date4u.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SearchProfileDto {
    private long id;
    private String nickname;
    private int hornlength;
    private int gender;
    private int age;

    private PhotoDto profilePhoto;

    public SearchProfileDto() { }


    public SearchProfileDto(long id, String nickname, int hornlength, int gender,
                            int age, @Nullable PhotoDto profilePhoto ) {
        this.id = id;

        this.nickname = nickname;
        this.hornlength = hornlength;
        this.gender = gender;
        this.age = age;
        this.profilePhoto = profilePhoto;
    }
        public long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getHornlength() {
        return hornlength;
    }

    public void setHornlength(int hornlength) {
        this.hornlength = hornlength;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public PhotoDto getProfilePhoto() {
        return profilePhoto;
    }

}
