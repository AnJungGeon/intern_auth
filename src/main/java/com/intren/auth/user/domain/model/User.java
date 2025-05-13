package com.intren.auth.user.domain.model;

import com.intren.auth.common.BaseEntity;
import com.intren.auth.user.domain.constant.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 100, name = "username")
    private String username;

    @Column(nullable = false, length = 100, name = "password")
    private String password;

    @Column(nullable = false, length = 100, name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    private UserRole role;

    private User(
            String username,
            String password,
            String nickname
    ){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = UserRole.USER;
    }

    public static User of(
            String username,
            String password,
            String nickname) {
        return new User(
                username,
                password,
                nickname
        );
    }



}
