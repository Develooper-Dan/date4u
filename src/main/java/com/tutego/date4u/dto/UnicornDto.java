package com.tutego.date4u.dto;


public class UnicornDto {

  private long id;

  private String email;

  private long profileID;


  protected UnicornDto() {}

  public UnicornDto(long id, String email, long profileID) {
    this.id = id;
    this.email = email;
    this.profileID = profileID;

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

  public long getProfileID() {
    return profileID;
  }

  public void setProfileID( long profileID ) {
    this.profileID = profileID;
  }


  @Override public String toString() {
    return "Unicorn[" + "id=" + id + ']';
  }

}