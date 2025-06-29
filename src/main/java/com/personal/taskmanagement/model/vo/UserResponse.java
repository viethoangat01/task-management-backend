package com.personal.taskmanagement.model.vo;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.constant.RoleUser;
import com.personal.taskmanagement.util.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private long id;
  private String name;
  private String email;
  private RoleUser role;

  public static UserResponse of(User user) {
    return MapperUtil.mapper.map(user, UserResponse.class);
  }
}