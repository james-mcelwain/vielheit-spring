package com.vielheit.core.exception;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.ws.rs.core.Response;

public enum ErrorCode {
    GLOBAL(2, Response.Status.INTERNAL_SERVER_ERROR),

    AUTHENTICATION(10, Response.Status.UNAUTHORIZED),
    JWT_TOKEN_EXPIRED(11, Response.Status.UNAUTHORIZED),
    UNAUTHORIZED(12, Response.Status.UNAUTHORIZED),
    ENTITY_EXISTS(13, Response.Status.BAD_REQUEST);

    private int code;
    private Response.Status status;

    ErrorCode(int code, Response.Status status) {
        this.code = code;
        this.status = status;
    }

    @JsonValue
    public int getErrorCode() {
        return code;
    }

    public Response.Status getStatus() {
        return this.status;
    }
}

