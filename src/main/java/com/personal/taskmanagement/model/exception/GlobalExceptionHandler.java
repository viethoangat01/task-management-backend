package com.personal.taskmanagement.model.exception;

import com.personal.taskmanagement.model.dto.response.RestResponse;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Handle InvalidValueException
  @ExceptionHandler(value = {InvalidValueException.class, HttpMessageNotReadableException.class})
  public ResponseEntity<RestResponse<String>> handleInvalidValueException(
      Exception ex) {
    RestResponse<String> response = new RestResponse<>();
    response.setTimestamp(Instant.now());
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setMessage(ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  // Handle Not Found Exception
  @ExceptionHandler(value = ResourceNotFoundException.class)
  public ResponseEntity<RestResponse<String>> handleResourceNotFoundException(
      ResourceNotFoundException ex) {
    RestResponse<String> response = new RestResponse<>();
    response.setTimestamp(Instant.now());
    response.setStatus(HttpStatus.NOT_FOUND.value());
    response.setMessage(ex.getMessage());

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
