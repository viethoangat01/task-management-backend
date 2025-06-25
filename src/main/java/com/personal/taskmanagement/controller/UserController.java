package com.personal.taskmanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.taskmanagement.model.dto.UserDto;
import com.personal.taskmanagement.model.vo.UserRegisterRequest;
import com.personal.taskmanagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing the current user's account
 */
@Tag(name = "Manage User API", description = "Manage the current user")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    /**
     * Register a new user's account.
     * 
     * @param userRegisterRequest the request body containing user registration details
     * @return ResponseEntity containing the created user's details
     */
    @Operation(
        summary = "Register new user's account.",
        description = "This API is used to register a new user by providing the necessary details in the request body.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "201", 
                description = "User successfully registered"
            )
            // @io.swagger.v3.oas.annotations.responses.ApiResponse(
            //     responseCode = "400", 
            //     description = "Invalid input data"
            // ),
            // @io.swagger.v3.oas.annotations.responses.ApiResponse(
            //     responseCode = "500", 
            //     description = "Internal server error"
            // )
        }
    )
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerAccount(
            @RequestBody @Valid UserRegisterRequest userRegisterRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.registerUser(UserDto.of(userRegisterRequest)));
        
    }
}
