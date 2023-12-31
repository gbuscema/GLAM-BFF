/*
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.glam.bff.openapi.user.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Nullable;

/**
 * UserDAO
 */
@JsonPropertyOrder({
  UserDAO.JSON_PROPERTY_USER_ID,
  UserDAO.JSON_PROPERTY_EMAIL,
  UserDAO.JSON_PROPERTY_PASSWORD,
  UserDAO.JSON_PROPERTY_FIRST_NAME,
  UserDAO.JSON_PROPERTY_LAST_NAME,
  UserDAO.JSON_PROPERTY_GENDER,
  UserDAO.JSON_PROPERTY_LOCATION_AGREEMENT,
  UserDAO.JSON_PROPERTY_FASHION_CANON,
  UserDAO.JSON_PROPERTY_STYLES,
  UserDAO.JSON_PROPERTY_LIVING_LOCATION,
  UserDAO.JSON_PROPERTY_SEASON
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-11-19T13:10:36.111141+01:00[Europe/Rome]")
public class UserDAO {
  public static final String JSON_PROPERTY_USER_ID = "userId";
  private Integer userId;

  public static final String JSON_PROPERTY_EMAIL = "email";
  private String email;

  public static final String JSON_PROPERTY_PASSWORD = "password";
  private String password;

  public static final String JSON_PROPERTY_FIRST_NAME = "firstName";
  private String firstName;

  public static final String JSON_PROPERTY_LAST_NAME = "lastName";
  private String lastName;

  public static final String JSON_PROPERTY_GENDER = "gender";
  private String gender;

  public static final String JSON_PROPERTY_LOCATION_AGREEMENT = "locationAgreement";
  private Boolean locationAgreement;

  public static final String JSON_PROPERTY_FASHION_CANON = "fashionCanon";
  private Long fashionCanon;

  public static final String JSON_PROPERTY_STYLES = "styles";
  private List<String> styles;

  public static final String JSON_PROPERTY_LIVING_LOCATION = "livingLocation";
  private String livingLocation;

  public static final String JSON_PROPERTY_SEASON = "season";
  private List<String> season;

  public UserDAO() {
  }

  public UserDAO userId(Integer userId) {
    
    this.userId = userId;
    return this;
  }

   /**
   * Get userId
   * @return userId
  **/
  @Nullable
  @JsonProperty(JSON_PROPERTY_USER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getUserId() {
    return userId;
  }


  @JsonProperty(JSON_PROPERTY_USER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUserId(Integer userId) {
    this.userId = userId;
  }


  public UserDAO email(String email) {
    
    this.email = email;
    return this;
  }

   /**
   * Get email
   * @return email
  **/
  @Nullable
  @JsonProperty(JSON_PROPERTY_EMAIL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getEmail() {
    return email;
  }


  @JsonProperty(JSON_PROPERTY_EMAIL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEmail(String email) {
    this.email = email;
  }


  public UserDAO password(String password) {
    
    this.password = password;
    return this;
  }

   /**
   * Get password
   * @return password
  **/
  @Nullable
  @JsonProperty(JSON_PROPERTY_PASSWORD)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getPassword() {
    return password;
  }


  @JsonProperty(JSON_PROPERTY_PASSWORD)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPassword(String password) {
    this.password = password;
  }


  public UserDAO firstName(String firstName) {
    
    this.firstName = firstName;
    return this;
  }

   /**
   * Get firstName
   * @return firstName
  **/
  @Nullable
  @JsonProperty(JSON_PROPERTY_FIRST_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getFirstName() {
    return firstName;
  }


  @JsonProperty(JSON_PROPERTY_FIRST_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


  public UserDAO lastName(String lastName) {
    
    this.lastName = lastName;
    return this;
  }

   /**
   * Get lastName
   * @return lastName
  **/
  @Nullable
  @JsonProperty(JSON_PROPERTY_LAST_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getLastName() {
    return lastName;
  }


  @JsonProperty(JSON_PROPERTY_LAST_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


  public UserDAO gender(String gender) {
    
    this.gender = gender;
    return this;
  }

   /**
   * Get gender
   * @return gender
  **/
  @Nullable
  @JsonProperty(JSON_PROPERTY_GENDER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getGender() {
    return gender;
  }


  @JsonProperty(JSON_PROPERTY_GENDER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setGender(String gender) {
    this.gender = gender;
  }


  public UserDAO locationAgreement(Boolean locationAgreement) {
    
    this.locationAgreement = locationAgreement;
    return this;
  }

   /**
   * Get locationAgreement
   * @return locationAgreement
  **/
  @Nullable
  @JsonProperty(JSON_PROPERTY_LOCATION_AGREEMENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getLocationAgreement() {
    return locationAgreement;
  }


  @JsonProperty(JSON_PROPERTY_LOCATION_AGREEMENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLocationAgreement(Boolean locationAgreement) {
    this.locationAgreement = locationAgreement;
  }


  public UserDAO fashionCanon(Long fashionCanon) {
    
    this.fashionCanon = fashionCanon;
    return this;
  }

   /**
   * Get fashionCanon
   * @return fashionCanon
  **/
  @Nullable
  @JsonProperty(JSON_PROPERTY_FASHION_CANON)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Long getFashionCanon() {
    return fashionCanon;
  }


  @JsonProperty(JSON_PROPERTY_FASHION_CANON)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFashionCanon(Long fashionCanon) {
    this.fashionCanon = fashionCanon;
  }


  public UserDAO styles(List<String> styles) {
    
    this.styles = styles;
    return this;
  }

  public UserDAO addStylesItem(String stylesItem) {
    if (this.styles == null) {
      this.styles = new ArrayList<>();
    }
    this.styles.add(stylesItem);
    return this;
  }

   /**
   * Get styles
   * @return styles
  **/
  @Nullable
  @JsonProperty(JSON_PROPERTY_STYLES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getStyles() {
    return styles;
  }


  @JsonProperty(JSON_PROPERTY_STYLES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStyles(List<String> styles) {
    this.styles = styles;
  }


  public UserDAO livingLocation(String livingLocation) {
    
    this.livingLocation = livingLocation;
    return this;
  }

   /**
   * Get livingLocation
   * @return livingLocation
  **/
  @Nullable
  @JsonProperty(JSON_PROPERTY_LIVING_LOCATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getLivingLocation() {
    return livingLocation;
  }


  @JsonProperty(JSON_PROPERTY_LIVING_LOCATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLivingLocation(String livingLocation) {
    this.livingLocation = livingLocation;
  }


  public UserDAO season(List<String> season) {
    
    this.season = season;
    return this;
  }

  public UserDAO addSeasonItem(String seasonItem) {
    if (this.season == null) {
      this.season = new ArrayList<>();
    }
    this.season.add(seasonItem);
    return this;
  }

   /**
   * Get season
   * @return season
  **/
  @Nullable
  @JsonProperty(JSON_PROPERTY_SEASON)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getSeason() {
    return season;
  }


  @JsonProperty(JSON_PROPERTY_SEASON)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSeason(List<String> season) {
    this.season = season;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDAO userDAO = (UserDAO) o;
    return Objects.equals(this.userId, userDAO.userId) &&
        Objects.equals(this.email, userDAO.email) &&
        Objects.equals(this.password, userDAO.password) &&
        Objects.equals(this.firstName, userDAO.firstName) &&
        Objects.equals(this.lastName, userDAO.lastName) &&
        Objects.equals(this.gender, userDAO.gender) &&
        Objects.equals(this.locationAgreement, userDAO.locationAgreement) &&
        Objects.equals(this.fashionCanon, userDAO.fashionCanon) &&
        Objects.equals(this.styles, userDAO.styles) &&
        Objects.equals(this.livingLocation, userDAO.livingLocation) &&
        Objects.equals(this.season, userDAO.season);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, email, password, firstName, lastName, gender, locationAgreement, fashionCanon, styles, livingLocation, season);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserDAO {\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    locationAgreement: ").append(toIndentedString(locationAgreement)).append("\n");
    sb.append("    fashionCanon: ").append(toIndentedString(fashionCanon)).append("\n");
    sb.append("    styles: ").append(toIndentedString(styles)).append("\n");
    sb.append("    livingLocation: ").append(toIndentedString(livingLocation)).append("\n");
    sb.append("    season: ").append(toIndentedString(season)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

