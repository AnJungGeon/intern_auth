package com.intren.auth.user.application.service;

import com.intren.auth.user.application.dto.request.SignupRequestDto;
import com.intren.auth.user.application.dto.response.SignupResponseDto;
import com.intren.auth.user.application.dto.response.UpdateUserRolesResponseDto;
import jakarta.validation.Valid;

public interface UserService {
    SignupResponseDto signup(@Valid SignupRequestDto requestDto);

    UpdateUserRolesResponseDto updateUserRoles(Long userId);
}
