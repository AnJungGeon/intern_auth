package com.intren.auth.authentication.application.service;

import com.intren.auth.authentication.application.dto.request.LoginRequestDto;
import com.intren.auth.authentication.application.dto.response.LoginResponseDto;
import jakarta.validation.Valid;

public interface AuthenticationService {
    LoginResponseDto login(@Valid LoginRequestDto requestDto);
}
