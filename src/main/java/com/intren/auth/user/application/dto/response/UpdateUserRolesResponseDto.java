package com.intren.auth.user.application.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateUserRolesResponseDto {
    private final String username;
    private final String nickname;
    private final List<RoleResponseDto> roles;

    public UpdateUserRolesResponseDto(
            String username,
            String nickname,
            List<RoleResponseDto> roles
    ){
        this.username = username;
        this.nickname = nickname;
        this.roles = roles;
    }

    public static UpdateUserRolesResponseDto of(
            String username,
            String nickname,
            List<RoleResponseDto> roles
    ){
        return new UpdateUserRolesResponseDto(username, nickname, roles);
    }
}
