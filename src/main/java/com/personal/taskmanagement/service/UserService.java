package com.personal.taskmanagement.service;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.mapper.UserMapper;
import com.personal.taskmanagement.model.constant.RoleUser;
import com.personal.taskmanagement.model.dto.response.PaginationMetadata;
import com.personal.taskmanagement.model.dto.response.user.UserPageResponse;
import com.personal.taskmanagement.model.dto.response.user.UserSearchResponse;
import com.personal.taskmanagement.repository.UserRepository;
import com.personal.taskmanagement.repository.specification.UserSpecification;
import com.personal.taskmanagement.util.FieldUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * Service class for managing users.
 */
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepo;
  private final UserMapper userMapper;

  /**
   * Searches for users based on optional filters such as name, email, and role, with support for
   * pagination and sorting.
   *
   * @param name      (optional) Filter by user's name (contains, case-insensitive)
   * @param email     (optional) Filter by user email (equals)
   * @param role      (optional) Filter by user role (e.g., MEMBER, ADMIN). If invalid, returns
   *                  empty result.
   * @param page      Page number for pagination (zero-based index)
   * @param size      Number of records per page
   * @param sortParam Sort field and direction (e.g., "createdAt,desc"). If invalid field,
   * @return {@link UserPageResponse} containing the list of users and pagination metadata
   */
  public UserPageResponse searchUser(String name, String email, String role, int page, int size,
      String sortParam) {
    // Create specification returns all records
    Specification<User> spec = (root, query, cb) -> cb.conjunction();

    // Filter by name (contains, case-insensitive)
    if (name != null && !name.isEmpty()) {
      spec = spec.and(UserSpecification.nameLike(name));
    }

    // Filter by email (equals)
    if (email != null && !email.isEmpty()) {
      spec = spec.and(UserSpecification.hasEmail(email));
    }

    // Filter by role (equals)
    RoleUser roleEnum = parseRole(role);
    if (roleEnum != null) {
      spec = spec.and(UserSpecification.hasRole(roleEnum));
    }

    // Sorting and paging
    Sort sort = generateSort(sortParam);
    Pageable pageable = PageRequest.of(page, size, sort);

    // Search in repo
    Page<User> pageUser = userRepo.findAll(spec, pageable);

    // Map to DTO
    List<UserSearchResponse> users = pageUser.getContent()
        .stream()
        .map(userMapper::mapToUserSearchResponse)
        .toList();

    PaginationMetadata metadata = new PaginationMetadata(page, size,
        (int) pageUser.getTotalElements(), pageUser.getTotalPages(), sortParam);

    UserPageResponse userPageResponse = new UserPageResponse();
    userPageResponse.setUsers(users);
    userPageResponse.setPagination(metadata);

    return userPageResponse;
  }

  private static Sort generateSort(String sortParam) {
    if (sortParam == null || sortParam.isBlank()) {
      return Sort.by("createdAt").descending();
    }

    String[] parts = sortParam.split(",");

    String sortField = parts[0].trim();
    List<String> fieldNames = FieldUtils.getFieldNames(User.class);
    sortField = fieldNames.contains(sortField) ? sortField : "createdAt";

    if (parts.length > 1 && parts[1].equalsIgnoreCase("asc")) {
      return Sort.by(sortField).ascending();
    }

    return Sort.by(sortField).descending();
  }

  private static RoleUser parseRole(String role) {
    if (role == null || role.isEmpty()) {
      return null;
    }

    try {
      return RoleUser.valueOf(role);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}
