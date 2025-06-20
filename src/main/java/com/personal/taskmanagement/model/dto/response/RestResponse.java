package com.personal.taskmanagement.model.dto.response;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse<T> {
  private Instant timestamp;
  private int status;
  private String message;
  private T data;

  public RestResponse() {
    this.timestamp = Instant.now();
  }

  public RestResponse(Instant timestamp, int status, String message, T data) {
    this.timestamp = Instant.now();
    this.status = status;
    this.message = message;
    this.data = data;
  }
}
