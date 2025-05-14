package com.intren.auth.user.application.service;

import com.intren.auth.authentication.infrastructure.password.PasswordEncoderUtil;
import com.intren.auth.user.application.dto.request.SignupRequestDto;
import com.intren.auth.user.application.dto.response.SignupResponseDto;
import com.intren.auth.user.application.dto.response.UpdateUserRolesResponseDto;
import com.intren.auth.user.application.mapper.UserMapper;
import com.intren.auth.user.application.validator.DuplicateUserValidator;
import com.intren.auth.user.domain.model.User;
import com.intren.auth.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DuplicateUserValidator duplicateUserValidator;

    @Override
    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        duplicateUserValidator.validateDuplicateUser(requestDto.getUsername());
        String encoded = PasswordEncoderUtil.encode(requestDto.getPassword());
        User user = UserMapper.toEntity(requestDto, encoded);
        userRepository.save(user);

        return UserMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UpdateUserRolesResponseDto updateUserRoles(Long userId) {
        User user = userRepository.findById(userId);

        user.updateUserRoles();

        return UserMapper.toUpdateRoleAdminResponseDto(user);
    }
}
