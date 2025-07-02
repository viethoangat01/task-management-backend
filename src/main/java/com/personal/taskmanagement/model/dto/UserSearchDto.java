package com.personal.taskmanagement.model.dto;

import com.personal.taskmanagement.entity.User;
import com.personal.taskmanagement.model.constant.RoleUser;
import com.personal.taskmanagement.util.MapperUtil;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDto {

  private long id;
  private String name;
  private String email;
  private RoleUser role;
  private Instant createdAt;

  public static UserSearchDto of(User user) {
    return MapperUtil.mapper.map(user, UserSearchDto.class);
  }
}
