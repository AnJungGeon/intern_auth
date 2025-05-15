package com.intren.auth.user.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
@Schema(description = "회원가입 요청 DTO")
public class SignupRequestDto {

    @Schema(description = "사용자 아이디", example = "JIN HO")
    @NotBlank(message = "아이디는 필수 입니다")
    private String username;

    @Schema(description = "사용자 비밀번호", example = "12341234")
    @NotBlank(message = "비밀번호는 필수 입니다")
    private String password;

    @Schema(description = "사용자 닉네임", example = "Mentos")
    @NotBlank(message = "닉네임은 필수 입니다")
    private String nickname;

    public SignupRequestDto(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

}
