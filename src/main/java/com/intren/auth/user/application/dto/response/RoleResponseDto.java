package com.intren.auth.user.application.dto.response;

import com.intren.auth.user.domain.constant.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "사용자 권한 정보")
public class RoleResponseDto {

    @Schema(description = "사용자 권한", examples = {"ADMIN", "USER"})
    private final UserRole role;

    public RoleResponseDto(UserRole role) {
        this.role = role;
    }
}
