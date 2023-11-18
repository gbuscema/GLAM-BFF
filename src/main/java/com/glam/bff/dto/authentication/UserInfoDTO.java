package com.glam.bff.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.glam.bff.dto.authentication.enums.LivingLocationEnum;
import com.glam.bff.dto.authentication.enums.StyleEnum;
import com.glam.bff.dto.garment.enums.SeasonEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

  @JsonProperty("email")
  private String email;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("gender")
  private String gender;

  @JsonProperty("locationAgreement")
  private Boolean locationAgreement;

  @JsonProperty("fashionCanon")
  private Long fashionCanon;

  @JsonProperty("styles")
  private List<StyleEnum> styles;

  @JsonProperty("livingLocation")
  private LivingLocationEnum livingLocation;

  @JsonProperty("season")
  private List<SeasonEnum> season;

}
