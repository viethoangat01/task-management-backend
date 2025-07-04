package com.personal.taskmanagement.service;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.dto.PaginationMetadataDto;
import com.personal.taskmanagement.model.dto.UserDto;
import com.personal.taskmanagement.model.dto.UserPageDto;
import com.personal.taskmanagement.model.dto.UserQueryDto;
import com.personal.taskmanagement.model.dto.UserSearchDto;
import com.personal.taskmanagement.model.exception.InvalidValueException;
import com.personal.taskmanagement.model.vo.UserResponse;
import com.personal.taskmanagement.repository.UserRepository;
import com.personal.taskmanagement.repository.specification.UserSpecification;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

  /**
   * Searches for users based on optional filters such as name, email, and role, with support for
   * pagination and sorting.
   *
   * @param queryParamsDto optional search filters, including: - User's name: contains,
   *                       case-insensitive - Email: exact match - Role: exact match Supports
   *                       pagination and sorting. Returns empty result if no user matches the
   *                       filters.
   * @return {@link UserPageDto} containing the list of users and pagination metadata
   */
  public UserPageDto searchUser(UserQueryDto queryParamsDto) {
    // Create specification returns all records
    Specification<User> spec = (root, query, cb) -> cb.conjunction();

    // Filter by name (contains, case-insensitive)
    if (queryParamsDto.getName() != null && !queryParamsDto.getName().isEmpty()) {
      spec = spec.and(UserSpecification.nameLike(queryParamsDto.getName()));
    }

    // Filter by email (equals)
    if (queryParamsDto.getEmail() != null && !queryParamsDto.getEmail().isEmpty()) {
      spec = spec.and(UserSpecification.hasEmail(queryParamsDto.getEmail()));
    }

    // Filter by role (equals)
    if (queryParamsDto.getRole() != null) {
      spec = spec.and(UserSpecification.hasRole(queryParamsDto.getRole()));
    }

    // Sorting and paging
    Sort sort = generateSort(queryParamsDto.getSort());
    Pageable pageable = PageRequest.of(queryParamsDto.getPage(), queryParamsDto.getSize(), sort);

    // Search in repo
    Page<User> pageUser = userRepo.findAll(spec, pageable);

    // Map to DTO
    List<UserSearchDto> users = pageUser.getContent()
        .stream()
        .map(UserSearchDto::of)
        .toList();

    PaginationMetadataDto metadata = new PaginationMetadataDto(queryParamsDto.getPage(),
        queryParamsDto.getSize(),
        (int) pageUser.getTotalElements(), pageUser.getTotalPages(), queryParamsDto.getSort());

    UserPageDto userPageDto = new UserPageDto();
    userPageDto.setUsers(users);
    userPageDto.setPagination(metadata);

    return userPageDto;
  }

  /**
   * Creates a new user in the system.
   * <p>
   * This method performs the following actions:
   *
   * @param userDto The data transfer object containing user details to create.
   * @return A {@link UserResponse} of the newly created user.
   */
  public UserDto createUser(UserDto userDto) {
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

    return UserDto.of(newUser);
  }

  private static Sort generateSort(String sortParam) {
    if (sortParam == null || sortParam.isBlank()) {
      return Sort.by("createdAt").descending();
    }

    String[] parts = sortParam.split(",");

    String sortField = parts[0].trim();
    sortField = sortField.isBlank() ? "createdAt" : sortField;

    if (parts.length > 1 && parts[1].equalsIgnoreCase("asc")) {
      return Sort.by(sortField).ascending();
    }

    return Sort.by(sortField).descending();
  }
}
