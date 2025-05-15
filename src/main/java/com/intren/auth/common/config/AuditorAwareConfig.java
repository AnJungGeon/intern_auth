package com.intren.auth.common.config;


import com.intren.auth.common.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class AuditorAwareConfig {

    @Bean
    public AuditorAware<Long> auditorAwareImpl() {
        return new AuditorAwareImpl();
    }

}
