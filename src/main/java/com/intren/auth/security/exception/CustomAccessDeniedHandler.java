package com.intren.auth.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.intren.auth.common.exception.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException{
        String message = getAccessDeniedMessage();

        ErrorResponse errorResponse = new ErrorResponse(SecurityErrorCode.ACCESS_DENIED, message);
        response.setStatus(SecurityErrorCode.ACCESS_DENIED.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }

    private String getAccessDeniedMessage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            for (var authority : authentication.getAuthorities()) {
                if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                    return "접근 권한이 없습니다.";
                }
            }
            return "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다";
        }

        return "접근 권한이 없습니다.";
    }
}
