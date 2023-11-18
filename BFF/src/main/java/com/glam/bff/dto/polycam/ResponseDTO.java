package com.glam.bff.dto.polycam;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseDTO {

  @JsonProperty("id")
  private String id;

  @JsonProperty("images")
  private Images images;

  @JsonProperty("zip")
  private String zip;
}

