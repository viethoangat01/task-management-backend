package com.personal.taskmanagement.service;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.dto.UserDto;
import com.personal.taskmanagement.model.exception.InvalidValueException;
import com.personal.taskmanagement.model.vo.UserResponse;
import com.personal.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for managing users.
 */
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepo;
  private final PasswordEncoder passwordEncoder;

  /**
   * Creates a new user in the system.
   * <p>
   * This method performs the following actions:
   *
   * @param userDto The data transfer object containing user details to create.
   * @return A {@link UserResponse} of the newly created user.
   */
  public UserResponse createUser(UserDto userDto) {
    // Check unique email
    if (userRepo.existsByEmail(userDto.getEmail())) {
      throw new InvalidValueException("email", "email is already in use");
    }

    // Create entity
    User newUser = new User();
    newUser.setName(userDto.getName());
    newUser.setEmail(userDto.getEmail());
    newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
    newUser.setRole(userDto.getRole());

    // Save to database
    userRepo.save(newUser);

    return UserResponse.of(newUser);
  }
}
