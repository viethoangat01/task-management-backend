package com.personal.taskmanagement.controller;

import com.personal.taskmanagement.model.dto.request.user.UserUpdateRequest;
import com.personal.taskmanagement.model.dto.response.user.UserResponse;
import com.personal.taskmanagement.service.UserService;
import com.personal.taskmanagement.util.annotation.ApiMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing the user's account
 */
@Tag(name = "Manage User API", description = "APIs for user management")
@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  /**
   * Update user by admin
   *
   * @param userUpdateRequest the request body containing new user details
   * @return ResponseEntity containing the updated user's data
   */
  @PutMapping
  @Operation(summary = "Update user account (Admin only).",
      description = "This API allows an administrator to update an existing user by providing required user data in the request body.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "User update request",
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UserUpdateRequest.class)
          )
      ),
      responses = {
          @ApiResponse(responseCode = "200", description = "User successfully updated"),
//          @ApiResponse(responseCode = "400", description = "Invalid request payload"),
//          @ApiResponse(responseCode = "403", description = "Access denied — admin only")
      })
  @ApiMessage("User updated successfully")
  public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
    return ResponseEntity.ok(userService.updateUser(userUpdateRequest));
  }
}
