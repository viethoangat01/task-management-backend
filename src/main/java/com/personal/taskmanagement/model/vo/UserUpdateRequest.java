package com.personal.taskmanagement.model.vo;

import com.personal.taskmanagement.model.constant.RoleUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

  @NotNull(message = "User ID must not be null")
  private Long id;

  private String name;

  @Email(message = "email is not a well-formed email")
  private String email;

  private String password;

  private RoleUser role;

}
