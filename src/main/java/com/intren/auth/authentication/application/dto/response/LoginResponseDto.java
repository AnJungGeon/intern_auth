package com.intren.auth.authentication.application.dto.response;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private String token;

    public LoginResponseDto(String token) {
        this.token = token;
    }
}
