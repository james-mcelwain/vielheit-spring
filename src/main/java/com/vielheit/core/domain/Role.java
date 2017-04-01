package com.vielheit.core.domain;

public enum Role {
    REFRESH_TOKEN, ADMIN, USER;

    public String authority() {
        return "ROLE_" + this.name();
    }
}
