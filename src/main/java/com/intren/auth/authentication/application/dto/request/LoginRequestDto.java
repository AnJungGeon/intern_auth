package com.intren.auth.authentication.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "로그인 요청 DTO")
public class LoginRequestDto {

    @Schema(description = "사용자 아이디", example = "JIN HO")
    @NotBlank(message = "아이디는 필수입니다")
    private String username;

    @Schema(description = "사용자 비밀번호", example = "12341234")
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;

    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
