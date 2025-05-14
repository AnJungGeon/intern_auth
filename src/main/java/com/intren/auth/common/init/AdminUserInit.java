package com.intren.auth.common.init;

import com.intren.auth.authentication.infrastructure.password.PasswordEncoderUtil;
import com.intren.auth.user.domain.model.User;
import com.intren.auth.user.infrastructure.persistence.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdminUserInit implements CommandLineRunner {
    private final JpaUserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if(!userRepository.existsByUsername("admin")) {
            User admin = User.of(
                    "admin",
                    PasswordEncoderUtil.encode("1234"),
                    "관리자"
            );
            admin.updateUserRoles();
            userRepository.save(admin);
        }
    }
}
