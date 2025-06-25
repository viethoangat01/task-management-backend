package com.personal.taskmanagement.service;

import org.springframework.stereotype.Service;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.dto.UserDto;
import com.personal.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing users.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    
    /**
     * Register a new user.
     *
     * @param userDto the user data transfer object containing user details
     * @return the registered user as a UserDto
     */
    public UserDto registerUser(UserDto userDto) {
        User newUser = new User();
        newUser.setName(userDto.getName());
        userRepository.save(newUser);
    
        if (log.isDebugEnabled()) {
            log.debug("Created Information for User: {}", newUser);
        }
    
        return UserDto.of(newUser);
    }
}
