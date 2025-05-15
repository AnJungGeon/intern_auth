package com.intren.auth.user.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.util.List;

@Getter
@Schema(description = "사용자 권한 변경 응답 DTO")
public class UpdateUserRolesResponseDto {

    @Schema(description = "사용자 아이디", example = "JIN HO")
    private final String username;

    @Schema(description = "사용자 닉네임", example = "Mentos")
    private final String nickname;

    @Schema(description = "변경된 사용자 권한 목록", example = "ADMIN")
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
