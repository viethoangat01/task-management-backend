package com.personal.taskmanagement.model.vo;

import com.personal.taskmanagement.model.constant.RoleUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Setter
public class UserCreateRequest {

  @NotBlank(message = "name must not be blank")
  private String name;

  @NotBlank(message = "email must not be blank")
  @Email(message = "email is not a well-formed email")
  private String email;

  @NotBlank(message = "password must not be blank")
  private String password;

  @NotNull(message = "role must not be blank")
  private RoleUser role;
}
