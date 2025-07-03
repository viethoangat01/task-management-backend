package com.personal.taskmanagement.model.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
@Setter
public class InvalidValueException extends RuntimeException {

  private String fieldName;

  private String message;

  public InvalidValueException(String fieldName, String message) {
    this.fieldName = fieldName;
    this.message = message;
  }
}
