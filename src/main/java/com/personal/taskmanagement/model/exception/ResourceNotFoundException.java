package com.personal.taskmanagement.model.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
  private String entityName;
  private String fieldName;
  private String fieldValue;

  public ResourceNotFoundException(String entityName, String fieldName,
      String fieldValue) {
    super(entityName + " not found with " + fieldName + ": " + fieldValue);
    this.entityName = entityName;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }
}
