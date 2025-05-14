package com.intren.auth.authentication.application.vaildator;

import com.intren.auth.authentication.domain.exception.AuthErrorCode;
import com.intren.auth.authentication.domain.exception.AuthException;
import com.intren.auth.authentication.infrastructure.password.PasswordEncoderUtil;
import com.intren.auth.user.domain.model.User;
import com.intren.auth.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginValidator {

    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;

    public User validateLogin(String username, String password) {
        User user = userRepository.findByUsername(username);

        if(!passwordEncoderUtil.isMatch(password, user.getPassword())){
            throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS);
        }
        return user;
    }
}
