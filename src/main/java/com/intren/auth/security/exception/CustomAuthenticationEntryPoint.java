package com.intren.auth.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intren.auth.common.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void commence(
          HttpServletRequest request,
          HttpServletResponse response,
          AuthenticationException authException) throws IOException, ServletException {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.setContentType("application/json;charset=utf-8");
          ErrorResponse errorResponse = new ErrorResponse(SecurityErrorCode.INVALID_TOKEN);
          objectMapper.writeValue(response.getWriter(), errorResponse);
  }
}
