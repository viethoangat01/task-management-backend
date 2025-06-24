package com.personal.taskmanagement.service;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.mapper.UserMapper;
import com.personal.taskmanagement.model.dto.request.user.UserCreateRequest;
import com.personal.taskmanagement.model.dto.response.user.UserResponse;
import com.personal.taskmanagement.model.exception.InvalidValueException;
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

  public UserResponse createUser(UserCreateRequest userDto) {
    // Check unique email
    if (userRepo.existsByEmail(userDto.getEmail())) {
      throw new InvalidValueException("Email address already in use");
    }

    // Convert to entity
    User user = userMapper.mapToUser(userDto);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // Save to database
    User savedUser = userRepo.save(user);

    return userMapper.mapToUserResponse(savedUser);
  }
}
