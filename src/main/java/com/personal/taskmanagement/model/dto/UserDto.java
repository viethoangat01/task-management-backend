package com.personal.taskmanagement.model.dto;

import com.personal.taskmanagement.model.constant.RoleUser;
import com.personal.taskmanagement.model.vo.UserUpdateRequest;
import com.personal.taskmanagement.util.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private long id;
  private String name;
  private String email;
  private String password;
  private RoleUser role;

  public static UserDto of(UserUpdateRequest userUpdateRequest) {
    return MapperUtil.mapper.map(userUpdateRequest, UserDto.class);
  }
}
