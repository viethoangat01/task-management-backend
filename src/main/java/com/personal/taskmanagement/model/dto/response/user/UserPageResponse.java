package com.personal.taskmanagement.model.dto.response.user;

import com.personal.taskmanagement.model.dto.response.PaginationMetadata;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPageResponse {

  private List<UserSearchResponse> users;
  private PaginationMetadata pagination;
}
