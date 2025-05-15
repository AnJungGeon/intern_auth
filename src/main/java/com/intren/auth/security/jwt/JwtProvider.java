package com.intren.auth.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;

    private SecretKey key;

    @PostConstruct
    public void keyInit() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }


    public String generateToken(Long userId, String roles) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getAccessExp());

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("role", roles)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public String generateRefreshToken(Long userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getRefreshExp());

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public void validateToken(String token) {
        if(token == null || token.isEmpty()) {
            throw new JwtException("토큰이 null 이거나 비어 있습니다");
        }
        try{
            getClaims(token);
        }catch(ExpiredJwtException e){
            throw new JwtException("만료된 토큰 입니다", e);
        }catch(JwtException e){
            throw new JwtException("유효하지 않은 토큰입니다", e);
        }
    }


    public Claims parseClaims(String token) {
        try{
            return getClaims(token);
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
