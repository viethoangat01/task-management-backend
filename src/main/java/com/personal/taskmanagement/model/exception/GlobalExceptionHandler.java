package com.personal.taskmanagement.model.exception;

import com.personal.taskmanagement.model.vo.RestResponse;
import java.time.Instant;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PropertyReferenceException.class)
  public ResponseEntity<RestResponse<String>> propertyReferenceException(
      PropertyReferenceException ex) {
    RestResponse<String> restResponse = new RestResponse<>();
    restResponse.setTimestamp(Instant.now());
    restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
    restResponse.setMessage("Invalid property reference");
    restResponse.setData(ex.getMessage());

    return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
  }

  // Handle base Exception
  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<RestResponse<String>> handleAllException(Exception e) {
    RestResponse<String> restResponse = new RestResponse<>();
    restResponse.setTimestamp(Instant.now());
    restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    restResponse.setMessage(e.getMessage());

    return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
