package com.intren.auth.user.domain.repository;

import com.intren.auth.user.domain.model.User;

public interface UserRepository {
    boolean existsByUsername(String username);

    void save(User user);

    User findByUsername(String username);

    User findById(Long userId);
}
