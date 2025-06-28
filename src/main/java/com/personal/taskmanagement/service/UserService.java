package com.personal.taskmanagement.service;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.mapper.UserMapper;
import com.personal.taskmanagement.model.dto.request.user.UserUpdateRequest;
import com.personal.taskmanagement.model.dto.response.user.UserResponse;
import com.personal.taskmanagement.model.exception.InvalidValueException;
import com.personal.taskmanagement.model.exception.ResourceNotFoundException;
import com.personal.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepo;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  /**
   * Updates an existing user's information based on the provided {@link UserUpdateRequest}.
   *
   * @param userDto the user update request containing new values to update. Must include a valid
   *                user ID.
   * @return a {@link UserResponse} object representing the updated user.
   * @throws ResourceNotFoundException if the user with the given ID is not found.
   * @throws InvalidValueException     if the new email (if provided) is already in use by another
   *                                   user.
   */
  public UserResponse updateUser(UserUpdateRequest userDto) {
    // Find an existing user
    User existingUser = userRepo.findById(userDto.getId())
        .orElseThrow(
            () -> new ResourceNotFoundException("User", "ID", String.valueOf(userDto.getId())));

    // Check unique and update email
    if (userDto.getEmail() != null && !existingUser.getEmail().equals(userDto.getEmail())) {
      if (userRepo.existsByEmail(userDto.getEmail())) {
        throw new InvalidValueException("Email address already in use");
      }

      existingUser.setEmail(userDto.getEmail());
    }

    // Updating
    if (userDto.getName() != null) {
      existingUser.setName(userDto.getName());
    }
    if (userDto.getPassword() != null) {
      existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
    }
    if (userDto.getRole() != null) {
      existingUser.setRole(userDto.getRole());
    }

    // Save to database
    existingUser = userRepo.save(existingUser);

    return userMapper.mapToUserResponse(existingUser);
  }
}
