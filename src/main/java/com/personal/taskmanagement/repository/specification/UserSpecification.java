package com.personal.taskmanagement.repository.specification;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.constant.RoleUser;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specific class for supporting filter, sort.
 */
public class UserSpecification {

  /**
   * Find user by name, case-insensitive (LIKE %keyword%)
   *
   * @param name name to find for (can be empty, not null)
   * @return Specification for filtering users by name
   */
  public static Specification<User> nameLike(String name) {
    return (root, query, cb)
        -> cb.like(root.get("name"), "%" + name + "%");
  }

  /**
   * Find user by email (EQUAL keyword)
   *
   * @param email email to find for (can be null or empty)
   * @return Specification for filtering users by email
   */
  public static Specification<User> hasEmail(String email) {
    return (root, query, cb)
        -> cb.equal(root.get("email"), email);
  }

  /**
   * Find user by role (EQUAL keyword)
   *
   * @param role email to find for (can be null or empty)
   * @return Specification for filtering users by role
   */
  public static Specification<User> hasRole(RoleUser role) {
    return (root, query, cb)
        -> cb.equal(root.get("role"), role);
  }
}
