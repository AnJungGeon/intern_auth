package com.intren.auth.authentication.presentation;

import com.intren.auth.authentication.application.dto.request.LoginRequestDto;
import com.intren.auth.authentication.application.dto.response.LoginResponseDto;
import com.intren.auth.authentication.application.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "AuthenticationController", description = "인증 도메인 관련 API")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @Operation(
            summary = "로그인",
            description = "username과 password를 입력받아 JWT 토큰을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                {
                  "token": "eKDIkdfjoakIdkfjpekdkcjdkoIOdjOKJDFOlLDKFJKL"
                }
                """))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "로그인 실패 (잘못된 계정 정보)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                {
                  "error": {
                    "code": "INVALID_CREDENTIALS",
                    "message": "아이디 또는 비밀번호가 올바르지 않습니다."
                  }
                }
                """))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(authenticationService.login(requestDto));
    }

}
