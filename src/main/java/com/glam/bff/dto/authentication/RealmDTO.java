package com.glam.bff.dto.authentication;

public class RealmDTO {

  private String id;
  private String realm;
  private int accessTokenLifespan;
  private boolean enabled;
  private String sslRequired;
  private boolean bruteForceProtected;
  private String loginTheme;
  private boolean eventsEnabled;
  private boolean adminEventsEnabled;

  // getters and setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRealm() {
    return realm;
  }

  public void setRealm(String realm) {
    this.realm = realm;
  }

  public int getAccessTokenLifespan() {
    return accessTokenLifespan;
  }

  public void setAccessTokenLifespan(int accessTokenLifespan) {
    this.accessTokenLifespan = accessTokenLifespan;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getSslRequired() {
    return sslRequired;
  }

  public void setSslRequired(String sslRequired) {
    this.sslRequired = sslRequired;
  }

  public boolean isBruteForceProtected() {
    return bruteForceProtected;
  }

  public void setBruteForceProtected(boolean bruteForceProtected) {
    this.bruteForceProtected = bruteForceProtected;
  }

  public String getLoginTheme() {
    return loginTheme;
  }

  public void setLoginTheme(String loginTheme) {
    this.loginTheme = loginTheme;
  }

  public boolean isEventsEnabled() {
    return eventsEnabled;
  }

  public void setEventsEnabled(boolean eventsEnabled) {
    this.eventsEnabled = eventsEnabled;
  }

  public boolean isAdminEventsEnabled() {
    return adminEventsEnabled;
  }

  public void setAdminEventsEnabled(boolean adminEventsEnabled) {
    this.adminEventsEnabled = adminEventsEnabled;
  }
}
