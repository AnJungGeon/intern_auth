package com.intren.auth.user.application.mapper;

import com.intren.auth.user.application.dto.request.SignupRequestDto;
import com.intren.auth.user.application.dto.response.RoleResponseDto;
import com.intren.auth.user.application.dto.response.SignupResponseDto;
import com.intren.auth.user.domain.model.User;

import java.util.List;

public class UserMapper {

    public static User toEntity(SignupRequestDto requestDto, String encodedPassword) {
        return User.of(
                requestDto.getUsername(),
                encodedPassword,
                requestDto.getNickname()
        );
    }

    public static SignupResponseDto toResponseDto(User user) {
        return SignupResponseDto.of(
                user.getUsername(),
                user.getNickname(),
                List.of(new RoleResponseDto(user.getRole()))
        );
    }
}
