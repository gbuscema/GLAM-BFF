package com.glam.bff.dto.authentication;

import java.util.List;
import java.util.Map;

public class KeycloakUserDTO {
  private String username;
  private boolean enabled;
  private List<String> realmRoles;
  private Map<String, List<String>> attributes;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public List<String> getRealmRoles() {
    return realmRoles;
  }

  public void setRealmRoles(List<String> realmRoles) {
    this.realmRoles = realmRoles;
  }

  public Map<String, List<String>> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, List<String>> attributes) {
    this.attributes = attributes;
  }
}
