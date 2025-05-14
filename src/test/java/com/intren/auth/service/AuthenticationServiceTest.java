package com.intren.auth.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intren.auth.authentication.application.dto.request.LoginRequestDto;
import com.intren.auth.authentication.application.dto.response.LoginResponseDto;
import com.intren.auth.authentication.application.service.AuthenticationServiceImpl;
import com.intren.auth.authentication.application.vaildator.LoginValidator;
import com.intren.auth.authentication.domain.exception.AuthErrorCode;
import com.intren.auth.authentication.domain.exception.AuthException;
import com.intren.auth.common.exception.ErrorResponse;
import com.intren.auth.security.jwt.JwtProvider;
import com.intren.auth.user.domain.model.User;
import com.intren.auth.user.infrastructure.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private LoginValidator loginValidator;

    @Mock
    private JwtProvider jwtProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private User user;

    @BeforeEach
    void setUp() {
        user = User.of("JIN HO", "12341234","Mentos");
        ReflectionTestUtils.setField(user, "id", 1L);
    }

    @Test
    void loginSuccess() {
        String fakeToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        LoginRequestDto requestDto = new LoginRequestDto("JIN HO", "12341234");
        when(loginValidator.validateLogin(anyString(), anyString()))
                .thenReturn(user);

        when(jwtProvider.generateToken(anyLong(), anyString()))
                .thenReturn(fakeToken);

        LoginResponseDto response = authenticationService.login(requestDto);

        try {
            String json = new ObjectMapper().writeValueAsString(response);
            System.out.println("응답 JSON: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assertNotNull(response.getToken());
        assertEquals(fakeToken, response.getToken());
    }


    @Test
    void loginFailure() {
        LoginRequestDto requestDto = new LoginRequestDto("JIN HO", "1111");

        when(loginValidator.validateLogin(anyString(), anyString()))
                .thenThrow(new AuthException(AuthErrorCode.INVALID_CREDENTIALS));

        AuthException exception = assertThrows(AuthException.class, () -> authenticationService.login(requestDto));
        assertEquals(AuthErrorCode.INVALID_CREDENTIALS.getMessage(), exception.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(AuthErrorCode.INVALID_CREDENTIALS);
        try {
            String json = new ObjectMapper().writeValueAsString(errorResponse);
            System.out.println("응답 JSON: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }



    @Test
    void login_Failure_UserNotFound() {
        LoginRequestDto requestDto = new LoginRequestDto("dddd", "1234");

        when(userRepository.findByUsername("NON_EXISTENT_USER"))
                .thenThrow(new AuthException(AuthErrorCode.INVALID_CREDENTIALS));

        AuthException exception = assertThrows(AuthException.class, () -> authenticationService.login(requestDto));
        assertEquals(AuthErrorCode.INVALID_CREDENTIALS.getMessage(), exception.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(AuthErrorCode.INVALID_CREDENTIALS);
        try {
            String json = new ObjectMapper().writeValueAsString(errorResponse);
            System.out.println("응답 JSON: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
