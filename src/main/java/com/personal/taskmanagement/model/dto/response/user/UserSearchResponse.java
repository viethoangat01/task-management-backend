package com.personal.taskmanagement.model.dto.response.user;

import com.personal.taskmanagement.model.constant.RoleUser;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchResponse {

  private long id;
  private String name;
  private String email;
  private RoleUser role;
  private Instant createdAt;
}
