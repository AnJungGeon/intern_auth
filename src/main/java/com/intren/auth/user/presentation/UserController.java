package com.intren.auth.user.presentation;

import com.intren.auth.user.application.dto.request.SignupRequestDto;
import com.intren.auth.user.application.dto.response.SignupResponseDto;
import com.intren.auth.user.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(
            @Valid @RequestBody SignupRequestDto requestDto) {

        SignupResponseDto response = userService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
