package com.personal.taskmanagement.model.dto;

import com.personal.taskmanagement.model.constant.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryDto {

  private String name;
  private String email;
  private RoleUser role;
  private int page;
  private int size;
  private String sort;
}
