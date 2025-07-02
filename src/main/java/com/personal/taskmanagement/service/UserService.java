package com.personal.taskmanagement.service;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.dto.PaginationMetadataDto;
import com.personal.taskmanagement.model.dto.UserPageDto;
import com.personal.taskmanagement.model.dto.UserQueryDto;
import com.personal.taskmanagement.model.dto.UserSearchDto;
import com.personal.taskmanagement.repository.UserRepository;
import com.personal.taskmanagement.repository.specification.UserSpecification;
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

  /**
   * Searches for users based on optional filters such as name, email, and role, with support for
   * pagination and sorting.
   *
   * @param queryParamsDto optional search filters, including: - User's name: contains,
   *                    case-insensitive - Email: exact match - Role: exact match Supports
   *                    pagination and sorting. Returns empty result if no user matches the
   *                    filters.
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
