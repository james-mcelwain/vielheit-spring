package com.vielheit.core.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    GLOBAL(2),

    AUTHENTICATION(10), JWT_TOKEN_EXPIRED(11), UNAUTHORIZED(12), ENTITY_EXISTS(13, EntityAlreadyExistsException.class);

    private int code;
    private Class<? extends Exception> exception;

    ErrorCode(int code) {
        this.code = code;
    }

    ErrorCode(int code, Class<? extends Exception> exception) {
        this.code = code;
        this.exception = exception;
    }

    @JsonValue
    public int getErrorCode() {
        return code;
    }
}

