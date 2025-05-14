package com.intren.auth.user.infrastructure.repository;

import com.intren.auth.authentication.domain.exception.AuthErrorCode;
import com.intren.auth.authentication.domain.exception.AuthException;
import com.intren.auth.user.domain.model.User;
import com.intren.auth.user.domain.repository.UserRepository;
import com.intren.auth.user.infrastructure.persistence.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public boolean existsByUsername(String username) {
        return jpaUserRepository.existsByUsername(username);
    }

    @Override
    public void save(User user) {
        jpaUserRepository.save(user);
    }

    @Override
    public User findByUsernmae(String username) {
        return jpaUserRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_CREDENTIALS));
    }
}
