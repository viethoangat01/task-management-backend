package com.personal.taskmanagement.model.exception;

import com.personal.taskmanagement.model.dto.response.RestResponse;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
