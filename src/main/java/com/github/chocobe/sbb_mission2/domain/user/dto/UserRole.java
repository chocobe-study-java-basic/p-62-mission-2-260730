package com.github.chocobe.sbb_mission2.domain.user.dto;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String role) {
        this.value = role;
    }

    private String value;

}
