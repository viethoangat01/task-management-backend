package com.personal.taskmanagement.model.exception;

import com.personal.taskmanagement.model.vo.RestResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler class for manage return exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  // Handle InvalidValueException
  @ExceptionHandler(InvalidValueException.class)
  public ResponseEntity<RestResponse<Map<String, String>>> handleInvalidValueException(
      InvalidValueException ex) {
    RestResponse<Map<String, String>> response = new RestResponse<>();
    response.setTimestamp(Instant.now());
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setMessage("Validation error");
    response.setData(Map.of(ex.getFieldName(), ex.getMessage()));

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<RestResponse<Map<String, String>>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    // Get invalid field names and messages
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      String fieldName = error.getField();
      String errorMessage = error.getDefaultMessage(); // The message was declared in annotation
      errors.put(fieldName, errorMessage);
    });

    RestResponse<Map<String, String>> response = new RestResponse<>();
    response.setTimestamp(Instant.now());
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setMessage("Validation error");
    response.setData(errors);

    return ResponseEntity.badRequest().body(response);
  }

  // Handle base Exception
  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<RestResponse<String>> handleAllException(Exception e) {
    RestResponse<String> restResponse = new RestResponse<>();
    restResponse.setTimestamp(Instant.now());
    restResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    restResponse.setMessage("Internal Server Error");
    restResponse.setData(e.getMessage());

    return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
