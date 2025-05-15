package com.intren.auth.user.presentation;

import com.intren.auth.user.application.dto.request.SignupRequestDto;
import com.intren.auth.user.application.dto.response.SignupResponseDto;
import com.intren.auth.user.application.dto.response.UpdateUserRolesResponseDto;
import com.intren.auth.user.application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User컨트롤러" ,description = "User 도메인 관련 API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @Operation(
            summary = "회원가입",
            description = "username, password, nickname을 받아 회원을 등록합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = SignupResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "이미 가입된 사용자",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                {
                  "error": {
                    "code": "USER_ALREADY_EXISTS",
                    "message": "이미 가입된 사용자입니다."
                  }
                }
                """)))
    })
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(
            @Valid @RequestBody SignupRequestDto requestDto) {

        SignupResponseDto response = userService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(
            summary = "사용자 권한 변경",
            description = "관리자가 사용자의 권한을 ADMIN으로 변경합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "권한 변경 성공",
                    content = @Content(schema = @Schema(implementation = UpdateUserRolesResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "접근 권한 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                {
                  "error": {
                    "code": "ACCESS_DENIED",
                    "message": "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다."
                  }
                }
                """))),
            @ApiResponse(responseCode = "404", description = "사용자 미존재",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                {
                  "error": {
                    "code": "USER_NOT_FOUND",
                    "message": "존재 하지 않는 사용자입니다."
                  }
                }
                """)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/users/{userId}/roles")
    public ResponseEntity<UpdateUserRolesResponseDto> updateUserRoles(
            @Valid @PathVariable Long userId){
        UpdateUserRolesResponseDto responseDto = userService.updateUserRoles(userId);
        return ResponseEntity.ok(responseDto);
    }
}
