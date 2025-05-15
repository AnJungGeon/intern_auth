package com.intren.auth.authentication.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "로그인 응답 DTO")
public class LoginResponseDto {

    @Schema(description = "JWT 액세스 토큰", example = "eKDIkdfjoakIdkfjpekdkcjdkoIOdjOKJDFOlLDKFJKL")
    private String token;

    public LoginResponseDto(String token) {
        this.token = token;
    }
}
