package com.personal.taskmanagement.model.vo;

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
