package com.glam.bff.controller;

import com.glam.bff.dto.authentication.UserInfoDTO;
import com.glam.bff.dto.authentication.UserLoginDTO;
import com.glam.bff.dto.authentication.UserLoginResponseDTO;
import com.glam.bff.dto.authentication.UserRegistrationDTO;
import com.glam.bff.service.AuthenticationService;
import com.glam.bff.utils.UserContext;
import com.glam.bff.utils.WardrobeVectorStoreBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.glam.bff.utils.Constants.USER_ID;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

  @Autowired
  private UserContext userContext;

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private WardrobeVectorStoreBuilder wardrobeVectorStoreBuilder;

  @PostMapping("/register")
  @Tag(name = "Authentication")
  @Operation(
          summary = "API to register the user in GLAM",
          description =
                  "Used to register the user in GLAM")
  public UserLoginResponseDTO registerUser(@RequestBody final UserRegistrationDTO body) throws Exception {

    return authenticationService.registerUser(body);

  }

  @PostMapping("/login")
  @Tag(name = "Authentication")
  @Operation(
          summary = "API to login the user in GLAM",
          description =
                  "Used to login the user in GLAM")
  public UserLoginResponseDTO loginUser(@RequestBody final UserLoginDTO body) throws Exception {

    return authenticationService.loginUser(body);

  }

  @GetMapping("/users/{userId}")
  @Tag(name = "Authentication")
  @Operation(
          summary = "API to get the user info in GLAM",
          description =
                  "Used to get the user info in GLAM")
  public UserInfoDTO getUserInfo(
          @PathVariable(USER_ID) final String userId) throws Exception {

    return authenticationService.getUserInfo(userId);

  }

  @PutMapping("/users/{userId}")
  @Tag(name = "Authentication")
  @Operation(
          summary = "API to update the user info in GLAM",
          description =
                  "Used to update the user info in GLAM")
  public UserInfoDTO updateUserInfo(
          @PathVariable(USER_ID) final int userId,
          @RequestBody final UserRegistrationDTO body) throws Exception {

    return authenticationService.updateUserInfo(userId, body);

  }

}
