package com.tutego.date4u.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class RegisterData {
  @NotNull
  @NotBlank
  @Email
  private String email;
  @NotNull
  @NotBlank
  private String password;
  @NotNull
  @NotBlank
  private String nickname;
  @NotNull
  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate birthdate;
  @NotNull
  private int hornlength;
  @NotNull
  private int gender;
  private Integer attractedToGender;
  private String description;

  protected RegisterData() {}

  public RegisterData(String email, String password, String nickname, LocalDate birthdate, int hornlength, int gender, Integer attractedToGender, String description) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.birthdate = birthdate;
    this.hornlength = hornlength;
    this.gender = gender;
    this.attractedToGender = attractedToGender;
    this.description = description;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail( String email ) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword( String password ) {
    this.password = password;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public int getHornlength() {
    return hornlength;
  }

  public void setHornlength(int hornlength) {
    this.hornlength = hornlength;
  }

  public int getGender() {
    return gender;
  }

  public void setGender(int gender) {
    this.gender = gender;
  }

  public Integer getAttractedToGender() {
    return attractedToGender;
  }

  public void setAttractedToGender(Integer attractedToGender) {
    this.attractedToGender = attractedToGender;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "RegisterData{" +
            "email='" + email + '\'' +
            ", nickname='" + nickname + '\'' +
            ", birthdate=" + birthdate +
            ", hornlength=" + hornlength +
            ", gender=" + gender +
            ", attractedToGender=" + attractedToGender +
            ", description='" + description + '\'' +
            '}';
  }
}