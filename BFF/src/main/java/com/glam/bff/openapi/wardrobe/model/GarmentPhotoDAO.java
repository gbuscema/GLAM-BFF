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


package com.glam.bff.openapi.wardrobe.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * GarmentPhotoDAO
 */
@JsonPropertyOrder({
  GarmentPhotoDAO.JSON_PROPERTY_PHOTO_ID,
  GarmentPhotoDAO.JSON_PROPERTY_GARMENT_ID,
  GarmentPhotoDAO.JSON_PROPERTY_USER_ID,
  GarmentPhotoDAO.JSON_PROPERTY_FILE_NAME,
  GarmentPhotoDAO.JSON_PROPERTY_PHOTO,
  GarmentPhotoDAO.JSON_PROPERTY_TEXTURE_URI
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-11-19T16:15:08.532396+01:00[Europe/Rome]")
public class GarmentPhotoDAO {
  public static final String JSON_PROPERTY_PHOTO_ID = "photoId";
  private String photoId;

  public static final String JSON_PROPERTY_GARMENT_ID = "garmentId";
  private String garmentId;

  public static final String JSON_PROPERTY_USER_ID = "userId";
  private String userId;

  public static final String JSON_PROPERTY_FILE_NAME = "fileName";
  private String fileName;

  public static final String JSON_PROPERTY_PHOTO = "photo";
  private byte[] photo;

  public static final String JSON_PROPERTY_TEXTURE_URI = "textureUri";
  private String textureUri;

  public GarmentPhotoDAO() {
  }

  public GarmentPhotoDAO photoId(String photoId) {
    
    this.photoId = photoId;
    return this;
  }

   /**
   * Get photoId
   * @return photoId
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PHOTO_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getPhotoId() {
    return photoId;
  }


  @JsonProperty(JSON_PROPERTY_PHOTO_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPhotoId(String photoId) {
    this.photoId = photoId;
  }


  public GarmentPhotoDAO garmentId(String garmentId) {
    
    this.garmentId = garmentId;
    return this;
  }

   /**
   * Get garmentId
   * @return garmentId
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_GARMENT_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getGarmentId() {
    return garmentId;
  }


  @JsonProperty(JSON_PROPERTY_GARMENT_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setGarmentId(String garmentId) {
    this.garmentId = garmentId;
  }


  public GarmentPhotoDAO userId(String userId) {
    
    this.userId = userId;
    return this;
  }

   /**
   * Get userId
   * @return userId
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_USER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getUserId() {
    return userId;
  }


  @JsonProperty(JSON_PROPERTY_USER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUserId(String userId) {
    this.userId = userId;
  }


  public GarmentPhotoDAO fileName(String fileName) {
    
    this.fileName = fileName;
    return this;
  }

   /**
   * Get fileName
   * @return fileName
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_FILE_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getFileName() {
    return fileName;
  }


  @JsonProperty(JSON_PROPERTY_FILE_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }


  public GarmentPhotoDAO photo(byte[] photo) {
    
    this.photo = photo;
    return this;
  }

   /**
   * Get photo
   * @return photo
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PHOTO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public byte[] getPhoto() {
    return photo;
  }


  @JsonProperty(JSON_PROPERTY_PHOTO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPhoto(byte[] photo) {
    this.photo = photo;
  }


  public GarmentPhotoDAO textureUri(String textureUri) {
    
    this.textureUri = textureUri;
    return this;
  }

   /**
   * Get textureUri
   * @return textureUri
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TEXTURE_URI)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTextureUri() {
    return textureUri;
  }


  @JsonProperty(JSON_PROPERTY_TEXTURE_URI)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTextureUri(String textureUri) {
    this.textureUri = textureUri;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GarmentPhotoDAO garmentPhotoDAO = (GarmentPhotoDAO) o;
    return Objects.equals(this.photoId, garmentPhotoDAO.photoId) &&
        Objects.equals(this.garmentId, garmentPhotoDAO.garmentId) &&
        Objects.equals(this.userId, garmentPhotoDAO.userId) &&
        Objects.equals(this.fileName, garmentPhotoDAO.fileName) &&
        Arrays.equals(this.photo, garmentPhotoDAO.photo) &&
        Objects.equals(this.textureUri, garmentPhotoDAO.textureUri);
  }

  @Override
  public int hashCode() {
    return Objects.hash(photoId, garmentId, userId, fileName, Arrays.hashCode(photo), textureUri);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GarmentPhotoDAO {\n");
    sb.append("    photoId: ").append(toIndentedString(photoId)).append("\n");
    sb.append("    garmentId: ").append(toIndentedString(garmentId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    fileName: ").append(toIndentedString(fileName)).append("\n");
    sb.append("    photo: ").append(toIndentedString(photo)).append("\n");
    sb.append("    textureUri: ").append(toIndentedString(textureUri)).append("\n");
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

