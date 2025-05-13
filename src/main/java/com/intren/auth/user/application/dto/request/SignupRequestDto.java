package com.intren.auth.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "아이디는 필수 입니다")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입니다")
    private String password;

    @NotBlank(message = "닉네임은 필수 입니다")
    private String nickname;



}
