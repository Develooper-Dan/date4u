package com.tutego.date4u.profile;


public class UnicornDto {

  private long id;

  private String email;

  private String password;

  private long profileID;
  private String jwt;

  protected UnicornDto() {}

  public UnicornDto(long id, String email, String password, long profileID, String jwt ) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.profileID = profileID;
    this.jwt = jwt;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public long getProfileID() {
    return profileID;
  }

  public void setProfileID( long profileID ) {
    this.profileID = profileID;
  }

  public String getJwt() {
    return jwt;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }


  @Override public String toString() {
    return "Unicorn[" + "id=" + id + ']';
  }
}