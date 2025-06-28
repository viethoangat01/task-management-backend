package com.personal.taskmanagement.model.dto.request.user;

import com.personal.taskmanagement.model.constant.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

  private long id;
  private String name;
  private String email;
  private String password;
  private RoleUser role;
}
