package com.personal.taskmanagement.controller;

import com.personal.taskmanagement.model.constant.RoleUser;
import com.personal.taskmanagement.model.dto.UserDto;
import com.personal.taskmanagement.model.dto.UserPageDto;
import com.personal.taskmanagement.model.dto.UserQueryDto;
import com.personal.taskmanagement.model.vo.UserCreateRequest;
import com.personal.taskmanagement.model.vo.UserResponse;
import com.personal.taskmanagement.model.vo.UserUpdateRequest;
import com.personal.taskmanagement.service.UserService;
import com.personal.taskmanagement.util.annotation.ApiMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
      })
  @ApiMessage("User updated successfully")
  public ResponseEntity<UserResponse> updateUser(
      @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
    UserDto updatedUser = userService.updateUser(UserDto.of(userUpdateRequest));
    return ResponseEntity.ok(UserResponse.of(updatedUser));
  }

  /**
   * Search for users based on filters.
   *
   * @param name  (optional) Filter by user's name (contains, case-insensitive)
   * @param email (optional) Filter by email address (contains, case-insensitive)
   * @param role  (optional) Filter by role (e.g., USER, ADMIN). If invalid, returns empty list.
   * @param page  (optional) Page number for pagination (zero-based, default is 0)
   * @param size  (optional) Number of records per page (default is 10)
   * @param sort  (optional) Sort field and direction, e.g. "createdAt,desc"
   * @return Paginated list of users matching the criteria
   */
  @GetMapping("/search")
  @Operation(summary = "Create a new user account (Admin only).",
      description = "This API allows an administrator to create a new user by providing required user data in the request body.",
      parameters = {
          @Parameter(name = "name", description = "Search by user name (contains, case-insensitive)", example = "john"),
          @Parameter(name = "email", description = "Search by email address", example = "john@example.com"),
          @Parameter(name = "role", description = "Filter by role (e.g., USER, ADMIN). If invalid, returns empty list", example = "ADMIN"),
          @Parameter(name = "page", description = "Current page number (zero-based indexing)", example = "0"),
          @Parameter(name = "size", description = "Number of records per page", example = "10"),
          @Parameter(name = "sort", description = "Sorting field and direction (e.g., createdAt,desc)", example = "createdAt,desc")
      },
      responses = {
          @ApiResponse(responseCode = "200", description = "User successfully created"),
      })
  @ApiMessage("Users fetched successfully")
  public ResponseEntity<UserPageDto> searchUser(
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "email", required = false) String email,
      @RequestParam(name = "role", required = false) RoleUser role,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "10") int size,
      @RequestParam(name = "sort", required = false, defaultValue = "createdAt,desc") String sort) {
    UserQueryDto queryParamsDto = new UserQueryDto(name, email, role, page, size, sort);
    return ResponseEntity.ok(userService.searchUser(queryParamsDto));
  }

  /**
   * Create new user by admin
   *
   * @param userCreateRequest the request body containing new user details
   * @return ResponseEntity containing the created user's data
   */
  @PostMapping
  @Operation(summary = "Create a new user account (Admin only).",
      description = "This API allows an administrator to create a new user by providing required user data in the request body.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "User creation request",
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = UserCreateRequest.class)
          )
      ),
      responses = {
          @ApiResponse(responseCode = "200", description = "User successfully created"),
      })
  @ApiMessage("User created successfully")
  public ResponseEntity<UserResponse> createUser(
      @RequestBody @Valid UserCreateRequest userCreateRequest) {
    UserDto newUser = userService.createUser(UserDto.of(userCreateRequest));
    return ResponseEntity.ok(UserResponse.of(newUser));
  }
}
