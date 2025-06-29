package com.personal.taskmanagement.util;

import com.personal.taskmanagement.model.vo.RestResponse;
import com.personal.taskmanagement.util.annotation.ApiMessage;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Instant;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice(basePackages = "com.personal.taskmanagement.controller")
public class FormatRestResponse implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    // Always format response
    return true;
  }

  @Override
  public Object beforeBodyWrite(
      Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {
    HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
    int status = servletResponse.getStatus();

    // If error, don't wrap
    if (status >= HttpServletResponse.SC_BAD_REQUEST) {
      return body;
    }

    // Return if body's type is RestResponse
    if (body instanceof RestResponse<?>) {
      return body;
    }

    ApiMessage apiMessage = returnType.getMethodAnnotation(ApiMessage.class);
    String message = apiMessage == null ? "Call API successfully" : apiMessage.value();

    return new RestResponse<>(Instant.now(), status, message, body);
  }
}
