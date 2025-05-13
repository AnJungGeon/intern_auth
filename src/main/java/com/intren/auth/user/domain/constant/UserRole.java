package com.intren.auth.user.domain.constant;

public enum UserRole {
    ADMIN("관리자"),
    USER("사용자");

    private final String description;

    UserRole(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
