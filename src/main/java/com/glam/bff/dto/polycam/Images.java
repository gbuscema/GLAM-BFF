package com.glam.bff.dto.polycam;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Images {

  @JsonProperty("displacement")
  private String displacement;

  @JsonProperty("normal_map")
  private String normalMap;

  @JsonProperty("roughness")
  private String roughness;

  @JsonProperty("texture")
  private String texture;
}
