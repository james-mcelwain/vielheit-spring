package com.vielheit.core;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    GLOBAL(2),

    AUTHENTICATION(10), JWT_TOKEN_EXPIRED(11), UNAUTHORIZED(12);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    @JsonValue
    public int getErrorCode() {
        return code;
    }
}

