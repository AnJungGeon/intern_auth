package com.intren.auth.common;

import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Optional;


public class AuditorAwareImpl implements AuditorAware<Long> {


    @Override
    @NonNull
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of(0L);
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Long userId) {
            return Optional.of(userId);
        }

        return Optional.of(0L);
    }

}