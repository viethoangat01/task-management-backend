package com.personal.taskmanagement.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPageDto {

  private List<UserSearchDto> users;

  private PaginationMetadataDto pagination;
}
