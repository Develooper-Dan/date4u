package com.tutego.date4u.dto;


public class SearchFormData {
    private String nickname;
    private int minAge;
    private int maxAge;
    private int minHornlength;
    private int maxHornlength;
    private Byte gender;
    private Integer attractedToGender;


    public SearchFormData() { }

    public SearchFormData(String nickname, int minHornlength, int maxHornlength, int minAge, int maxAge, Byte gender,
                          Integer attractedToGender ) {
        this.nickname = nickname;
        this.minHornlength = minHornlength;
        this.maxHornlength = maxHornlength;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.gender = gender;
        this.attractedToGender = attractedToGender;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAttractedToGender() {
        return attractedToGender;
    }

    public void setAttractedToGender(Integer attractedToGender) {
        this.attractedToGender = attractedToGender;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getMinHornlength() {
        return minHornlength;
    }

    public void setMinHornlength(int minHornlength) {
        this.minHornlength = minHornlength;
    }

    public int getMaxHornlength() {
        return maxHornlength;
    }

    public void setMaxHornlength(int maxHornlength) {
        this.maxHornlength = maxHornlength;
    }
}
