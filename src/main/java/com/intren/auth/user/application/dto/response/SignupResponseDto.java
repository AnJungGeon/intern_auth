package com.intren.auth.user.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


import java.util.List;

@Getter
@Schema(description = "회원가입 응답 DTO")
public class SignupResponseDto {


    @Schema(description = "사용자 ID", example = "JIN HO")
    private final String username;

    @Schema(description = "사용자 닉네임", example = "Mentos")
    private final String nickname;

    @Schema(description = "사용자 권한 목록")
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
