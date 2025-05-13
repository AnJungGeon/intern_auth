package com.intren.auth.user.application.validator;

import com.intren.auth.user.domain.exception.UserDuplicatedException;
import com.intren.auth.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DuplicateUserValidator	 {

    private final UserRepository userRepository;

    public void validateDuplicateUser(String username) {
        if(userRepository.existsByUsername(username)){
            throw new UserDuplicatedException();
        }
    }
}
