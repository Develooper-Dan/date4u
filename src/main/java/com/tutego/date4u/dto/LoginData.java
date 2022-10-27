package com.tutego.date4u.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginData {
  @NotNull
  @NotBlank
  @Email
  private String email;
  @NotNull
  @NotBlank
  private String password;


  protected LoginData() {}

  public LoginData( String email, String password) {
    this.email = email;
    this.password = password;
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


  @Override
  public String toString() {
    return "LoginData{" +
            "email='" + email + '\''  +
            '}';
  }
}