package com.glam.bff.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenDTO {

          private String access_token;
          private String expires_in;
          private String refresh_expires_in;
          private String refresh_token;
          private String token_type;
          @JsonProperty("not-before-policy")
          private String not_before_policy;
          private String session_state;
          private String scope;
}
