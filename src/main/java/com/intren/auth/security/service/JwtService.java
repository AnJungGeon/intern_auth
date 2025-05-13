package com.intren.auth.security.service;

import com.intren.auth.security.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    public String getAccessToken(String authHeader) {
        if(!authHeader.startsWith(BEARER_PREFIX)) {
            throw new IllegalArgumentException("Bearer 토큰이 아닙니다 받은 헤더 : " + authHeader);
        }
        return authHeader.substring(BEARER_PREFIX.length());
    }

    public boolean validateToken(String token) {
        try {
            jwtProvider.validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Long extractUserId(String token) {
        Claims claims = jwtProvider.parseClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    public String extractUserRole(String token) {
        Claims claims = jwtProvider.parseClaims(token);
        return claims.get("role", String.class);
    }
}
