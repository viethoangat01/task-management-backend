package com.personal.taskmanagement.service;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.dto.UserDto;
import com.personal.taskmanagement.model.exception.InvalidValueException;
import com.personal.taskmanagement.model.vo.UserResponse;
import com.personal.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepo;
  private final PasswordEncoder passwordEncoder;

  /**
   * Updates an existing user's information based on the provided {@link UserDto}.
   *
   * @param userDto the user update request containing new values to update. Must include a valid
   *                user ID.
   * @return a {@link UserResponse} object representing the updated user.
   * @throws InvalidValueException if the user with the given ID is not found.
   * @throws InvalidValueException if the new email (if provided) is already in use by another
   *                               user.
   */
  public UserDto updateUser(UserDto userDto) {
    // Find an existing user
    User existingUser = userRepo.findById(userDto.getId())
        .orElseThrow(
            () -> new InvalidValueException("id",
                "id" + userDto.getId() + "is not match with existing user "));

    // Check unique and update email
    if (userDto.getEmail() != null && !existingUser.getEmail().equals(userDto.getEmail())) {
      if (userRepo.existsByEmail(userDto.getEmail())) {
        throw new InvalidValueException("email", "email is already in use");
      }

      existingUser.setEmail(userDto.getEmail());
    }

    // Updating
    if (userDto.getName() != null && !userDto.getName().isBlank()) {
      existingUser.setName(userDto.getName());
    }
    if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
      existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
    }
    if (userDto.getRole() != null) {
      existingUser.setRole(userDto.getRole());
    }

    // Save to database
    existingUser = userRepo.save(existingUser);

    return UserDto.of(existingUser);
  }
}
