package com.intren.auth.user.application.dto.response;

import com.intren.auth.user.domain.constant.UserRole;
import lombok.Getter;

@Getter
public class RoleResponseDto {
    private final UserRole role;

    public RoleResponseDto(UserRole role) {
        this.role = role;
    }
}
