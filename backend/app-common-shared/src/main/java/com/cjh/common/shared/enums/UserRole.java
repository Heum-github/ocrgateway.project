package com.cjh.common.shared.enums;

import java.util.Optional;

public enum UserRole {
    
    ADM("관리자"),
    USR("사용자"),
    DEV("개발자");
    // GUEST("손님");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static boolean isAdmin(String userRole) {
        return Optional.ofNullable(userRole)
                       .map(role -> UserRole.ADM.name().equals(role))
                       .orElse(false);
    }

    public static boolean isDeveloper(String userRole) {
        return Optional.ofNullable(userRole)
                       .map(role -> UserRole.DEV.name().equals(role))
                       .orElse(false);
    }

    public static boolean isUser(String userRole) {
        return Optional.ofNullable(userRole)
                       .map(role -> UserRole.USR.name().equals(role))
                       .orElse(false);
    }
}