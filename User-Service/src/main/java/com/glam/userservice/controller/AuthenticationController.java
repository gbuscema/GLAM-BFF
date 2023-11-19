package com.glam.userservice.controller;

import com.glam.userservice.dao.UserDAO;
import com.glam.userservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

import static com.glam.userservice.utils.Constants.USER_ID;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/register")
  @Tag(name = "Authentication")
  @Operation(
          summary = "API to register the user in GLAM",
          description =
                  "Used to register the user in GLAM")
  public HttpStatusCode registerUser(@RequestBody final UserDAO body) throws Exception {

    authenticationService.registerUser(body);

    return HttpStatusCode.valueOf(200);
  }

  @PostMapping("/login")
  @Tag(name = "Authentication")
  @Operation(
          summary = "API to login the user in GLAM",
          description =
                  "Used to login the user in GLAM")
  public UserDAO loginUser(@RequestBody final UserDAO body) throws Exception {

    return authenticationService.loginUser(body);

  }

  @GetMapping("/users/{userId}")
  @Tag(name = "Authentication")
  @Operation(
          summary = "API to get the user info in GLAM",
          description =
                  "Used to get the user info in GLAM")
  public UserDAO getUserInfo(
          @PathVariable(USER_ID) final String userId) throws Exception {

    return authenticationService.getUserInfo(userId);

  }

  @PutMapping("/users/{userId}")
  @Tag(name = "Authentication")
  @Operation(
          summary = "API to update the user info in GLAM",
          description =
                  "Used to update the user info in GLAM")
  public UserDAO updateUserInfo(
          @PathVariable(USER_ID) final int userId,
          @RequestBody final UserDAO body) throws Exception {

    return authenticationService.updateUserInfo(userId, body);

  }

}
