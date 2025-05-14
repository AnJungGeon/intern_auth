package com.intren.auth.authentication.application.service;

import com.intren.auth.authentication.application.dto.request.LoginRequestDto;
import com.intren.auth.authentication.application.dto.response.LoginResponseDto;
import com.intren.auth.authentication.application.vaildator.LoginValidator;
import com.intren.auth.authentication.infrastructure.password.PasswordEncoderUtil;
import com.intren.auth.security.jwt.JwtProvider;
import com.intren.auth.security.service.JwtService;
import com.intren.auth.user.domain.model.User;
import com.intren.auth.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final LoginValidator loginValidator;
    private final JwtProvider jwtProvider;


    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = loginValidator.validateLogin(requestDto.getUsername(), requestDto.getPassword());

        String token = jwtProvider.generateToken(user.getId(), user.getRole().name());

        return new LoginResponseDto(token);
    }
}
