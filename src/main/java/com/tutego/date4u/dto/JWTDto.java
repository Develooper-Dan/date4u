package com.tutego.date4u.dto;


public class JWTDto {


  private String jwt;

  protected JWTDto() {}

  public JWTDto(String jwt) {
    this.jwt = jwt;
  }

  public String getJwt() {
    return jwt;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }

}