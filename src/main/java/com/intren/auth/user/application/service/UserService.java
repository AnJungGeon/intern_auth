package com.intren.auth.user.application.service;

import com.intren.auth.user.application.dto.request.SignupRequestDto;
import com.intren.auth.user.application.dto.response.SignupResponseDto;
import jakarta.validation.Valid;

public interface UserService {
    SignupResponseDto signup(@Valid SignupRequestDto requestDto);
}
