package com.intren.auth.user.application.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class SignupResponseDto {

    private final String username;
    private final String nickname;
    private final List<RoleResponseDto> roles;

    public SignupResponseDto(
            String username,
            String nickname,
            List<RoleResponseDto> roles
    ){
        this.username = username;
        this.nickname = nickname;
        this.roles = roles;
    }

    public static SignupResponseDto of(
            String username,
            String nickname,
            List<RoleResponseDto> roles
    ){
        return new SignupResponseDto(username, nickname, roles);
    }
}
