package com.personal.taskmanagement.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationMetadata {

  private int page;
  private int size;
  private int totalElements;
  private int totalPages;
  private String sort;
}
