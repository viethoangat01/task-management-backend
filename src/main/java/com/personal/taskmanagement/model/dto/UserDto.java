package com.personal.taskmanagement.model.dto;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.constant.RoleUser;
import com.personal.taskmanagement.model.vo.UserCreateRequest;
import com.personal.taskmanagement.model.vo.UserUpdateRequest;
import com.personal.taskmanagement.util.MapperUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO representing a user
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  @NotBlank(message = "id should not be null or empty")
  private Long id;

  @Size(max = 50)
  private String name;

  private String email;

  private String password;

  private RoleUser role;

  public static UserDto of(UserCreateRequest userCreateRequest) {
    return MapperUtil.mapper.map(userCreateRequest, UserDto.class);
  }

  public static UserDto of(UserUpdateRequest userUpdateRequest) {
    return MapperUtil.mapper.map(userUpdateRequest, UserDto.class);
  }

  public static UserDto of(User user) {
    return MapperUtil.mapper.map(user, UserDto.class);
  }
}
