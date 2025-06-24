package com.personal.taskmanagement.controller;

import com.personal.taskmanagement.model.dto.response.user.UserPageResponse;
import com.personal.taskmanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
//          @ApiResponse(responseCode = "400", description = "Invalid request payload"),
//          @ApiResponse(responseCode = "403", description = "Access denied — admin only")
      })

  public ResponseEntity<UserPageResponse> createUser(
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "email", required = false) String email,
      @RequestParam(name = "role", required = false) String role,
      @RequestParam(name = "page", required = false, defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "10") int size,
      @RequestParam(name = "sort", required = false, defaultValue = "createdAt,desc") String sort) {
    return ResponseEntity.ok(userService.searchUser(name, email, role, page, size, sort));
  }
}
