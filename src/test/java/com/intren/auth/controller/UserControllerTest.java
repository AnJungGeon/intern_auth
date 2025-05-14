package com.intren.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intren.auth.user.application.dto.request.SignupRequestDto;
import com.intren.auth.user.application.dto.response.RoleResponseDto;
import com.intren.auth.user.application.dto.response.SignupResponseDto;
import com.intren.auth.user.application.dto.response.UpdateUserRolesResponseDto;
import com.intren.auth.user.application.service.UserServiceImpl;
import com.intren.auth.user.domain.constant.UserRole;
import com.intren.auth.user.domain.exception.UserDuplicatedException;
import com.intren.auth.user.domain.exception.UserNotFoundException;
import com.intren.auth.user.presentation.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void signupSuccessTest() throws Exception {
        SignupRequestDto requestDto = new SignupRequestDto("JIN HO", "12341234", "Mentos");

        List<RoleResponseDto> roles = List.of(new RoleResponseDto(UserRole.valueOf("USER")));


        SignupResponseDto responseDto = new SignupResponseDto("JIN HO", "Mentos", roles);

        when(userService.signup(any(SignupRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("JIN HO"))
                .andExpect(jsonPath("$.nickname").value("Mentos"))
                .andExpect(jsonPath("$.roles[0].role").value("USER"));

    }

    @Test
    void signupFailTset() throws Exception {
        SignupRequestDto requestDto = new SignupRequestDto("JIN HO", "12341234", "Mentos");

        lenient().when(userService.signup(any(SignupRequestDto.class)))
                .thenThrow(new UserDuplicatedException());

        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error.code").value("USER_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.error.message").value("이미 가입된 사용자입니다."));
    }


    @Test
    void updateUserRolesSuccess() throws Exception {
        Long userId = 1L;
        List<RoleResponseDto> roles = List.of(new RoleResponseDto(UserRole.valueOf("ADMIN")));
        UpdateUserRolesResponseDto responseDto = UpdateUserRolesResponseDto.of("JIN HO", "Mentos", roles);

        when(userService.updateUserRoles(userId)).thenReturn(responseDto);

        mockMvc.perform(patch("/admin/users/{userId}/roles", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("JIN HO"))
                .andExpect(jsonPath("$.roles[0].role").value("ADMIN"));
    }



    @Test
    void updateUserRolesFail() throws Exception {
        Long userId = 1L;

        mockMvc.perform(patch("/admin/users/{userId}/roles", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void updateUserRolesFailUserNotFound() throws Exception {
        Long userId = 999L;

        when(userService.updateUserRoles(userId)).thenThrow(new UserNotFoundException());

        mockMvc.perform(patch("/admin/users/{userId}/roles", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.code").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").value("존재 하지 않는 사용자입니다."));
    }
}
