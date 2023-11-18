package com.glam.bff.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDTO extends UserInfoDTO {

  @JsonProperty("userId")
  private String userId;

}
