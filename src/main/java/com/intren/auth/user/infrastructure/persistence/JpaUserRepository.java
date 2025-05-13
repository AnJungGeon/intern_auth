package com.intren.auth.user.infrastructure.persistence;

import com.intren.auth.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
}
