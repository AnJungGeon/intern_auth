package com.intren.auth.security.config;


import com.intren.auth.security.filter.AuthFilter;
import com.intren.auth.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http
                .csrf(csrf ->csrf.disable())
                .authorizeHttpRequests(auth ->auth
                        .requestMatchers("/login", "signup").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new AuthFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
                .build();

    }
}
