package com.personal.taskmanagement.model.vo;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {

  private Instant timestamp;
  private int status;
  private String message;
  private T data;
}
