package com.intren.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intren.auth.authentication.infrastructure.password.PasswordEncoderUtil;
import com.intren.auth.common.exception.ErrorResponse;
import com.intren.auth.user.application.dto.request.SignupRequestDto;
import com.intren.auth.user.application.dto.response.SignupResponseDto;
import com.intren.auth.user.application.dto.response.UpdateUserRolesResponseDto;
import com.intren.auth.user.application.service.UserServiceImpl;
import com.intren.auth.user.application.validator.DuplicateUserValidator;
import com.intren.auth.user.domain.exception.UserDuplicatedException;
import com.intren.auth.user.domain.exception.UserErrorCode;
import com.intren.auth.user.domain.exception.UserNotFoundException;
import com.intren.auth.user.domain.model.User;
import com.intren.auth.user.infrastructure.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private DuplicateUserValidator duplicateUserValidator;

    @Mock
    private PasswordEncoderUtil passwordEncoderUtil;

    private User user;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        user = User.of("JIN HO", "12341234", "Mentos");
        ReflectionTestUtils.setField(user, "id", 1L);
    }


    @Test
    void signupSuccess() {
        SignupRequestDto requestDto = new SignupRequestDto("JIN HO", "12341234", "Mentos");

        doNothing().when(duplicateUserValidator).validateDuplicateUser("JIN HO");

        try (MockedStatic<PasswordEncoderUtil> mockedStatic = Mockito.mockStatic(PasswordEncoderUtil.class)) {
            mockedStatic.when(() -> PasswordEncoderUtil.encode("12341234"))
                    .thenReturn("encoded-password");

            SignupResponseDto response = userService.signup(requestDto);

            assertEquals("JIN HO", response.getUsername());
            assertEquals("Mentos", response.getNickname());
            assertEquals("USER", response.getRoles().get(0).getRole().name());

            try {
                String json = new ObjectMapper().writeValueAsString(response);
                System.out.println("응답 JSON: " + json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    void signup_Failure_DuplicatedUser() {
        SignupRequestDto requestDto = new SignupRequestDto("JIN HO", "12341234", "Mentos");

        doThrow(new UserDuplicatedException()).when(duplicateUserValidator).validateDuplicateUser("JIN HO");

        assertThrows(UserDuplicatedException.class, () -> userService.signup(requestDto));

        ErrorResponse errorResponse = new ErrorResponse(UserErrorCode.USER_ALREADY_EXISTS);
        try {
            String json = new ObjectMapper().writeValueAsString(errorResponse);
            System.out.println("응답 JSON: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @Test
    void updateUserRoles_Success() {

        when(userRepository.findById(1L)).thenReturn(user);

        UpdateUserRolesResponseDto response = userService.updateUserRoles(1L);

        assertEquals("JIN HO", response.getUsername());
        assertEquals("Mentos", response.getNickname());
        assertEquals("ADMIN", response.getRoles().get(0).getRole().name());

        try {
            String json = new ObjectMapper().writeValueAsString(response);
            System.out.println("응답 JSON: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateUserRoles_Failure_UserNotFound() {
        Long userId = 999L;
        when(userRepository.findById(userId)).thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class, () -> userService.updateUserRoles(userId));


        ErrorResponse errorResponse = new ErrorResponse(UserErrorCode.USER_NOT_FOUND);
        try {
            String json = new ObjectMapper().writeValueAsString(errorResponse);
            System.out.println("응답 JSON: " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
