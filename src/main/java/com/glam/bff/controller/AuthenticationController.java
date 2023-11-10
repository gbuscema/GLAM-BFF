package com.glam.bff.controller;

import com.glam.bff.dto.authentication.AccessTokenDTO;
import com.glam.bff.dto.authentication.KeycloakUserDTO;
import com.glam.bff.dto.authentication.ResetPasswordDTO;
import com.glam.bff.dto.authentication.UserRegistrationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

  private final String realm = "master";

  @PostMapping("/register")
  @Tag(name = "Authentication")
  @Operation(
          summary = "API to register the user in GLAM",
          description =
                  "Used to register the user in GLAM")
  public HttpStatusCode registerUser(@RequestBody final UserRegistrationDTO body) throws Exception {
    AccessTokenDTO adminAccessToken = getAdminAccessToken();
    ResponseEntity<String> response = createUser(adminAccessToken, body);
    String userId = parseUserIdFromRegistrationReponse(response);
    response = resetPassword(adminAccessToken, userId, body.getPassword());
    return response.getStatusCode();
  }

  private String parseUserIdFromRegistrationReponse(ResponseEntity<String> response) {
    URI locationHeader = response.getHeaders().getLocation();
    String userId = "";
    if (locationHeader != null) {
      // Get the path of the "Location" URL
      String locationPath = locationHeader.getPath();

      // Get the last segment of the path
      userId = locationPath.substring(locationPath.lastIndexOf('/') + 1);
    }
    return userId;
  }

  private ResponseEntity<String> createUser(AccessTokenDTO adminAccessToken, UserRegistrationDTO body) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + adminAccessToken.getAccess_token());

    KeycloakUserDTO user = new KeycloakUserDTO();
    user.setUsername(body.getEmail());
    user.setEnabled(true);
    user.setRealmRoles(Arrays.asList("user", "offline_access"));
    // set attributes
    Map<String, List<String>> attributes = new HashMap<>();
    attributes.put("email", Collections.singletonList(body.getEmail()));
    attributes.put("firstName", Collections.singletonList(body.getFirstName()));
    attributes.put("lastName", Collections.singletonList(body.getLastName()));
    attributes.put("locationAgreement", Collections.singletonList(body.getLocationAgreement()));
    user.setAttributes(attributes);

    HttpEntity<KeycloakUserDTO> request = new HttpEntity<>(user, headers);

    String url = String.format("http://localhost:8080/auth/admin/realms/%s/users", realm);
    return restTemplate.postForEntity(url, request, String.class);
  }

  private ResponseEntity<String> resetPassword(AccessTokenDTO adminAccessToken, String userId, String newPassword) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + adminAccessToken.getAccess_token());

    ResetPasswordDTO passwordDTO = new ResetPasswordDTO();
    passwordDTO.setType("password");
    passwordDTO.setTemporary(false);
    passwordDTO.setValue(newPassword);

    HttpEntity<ResetPasswordDTO> request = new HttpEntity<>(passwordDTO, headers);

    String url = String.format("http://localhost:8080/auth/admin/realms/%s/users/%s/reset-password", realm, userId);

    return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
  }

  private AccessTokenDTO getAdminAccessToken() {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("client_id", "admin-cli");
    //map.add("client_secret", "fadig4858ih3bfa7823hdfnuaisdf783");
    map.add("username", "glam-admin");
    map.add("password", "glam-admin");
    map.add("grant_type", "password");

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

    String url = "http://localhost:8080/auth/realms/master/protocol/openid-connect/token";

    ResponseEntity<AccessTokenDTO> response = restTemplate.postForEntity(url, request, AccessTokenDTO.class);
    return response.getBody();
  }

}
