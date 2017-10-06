package com.vielheit.core.exception;

import org.springframework.http.HttpStatus;

import javax.ws.rs.core.Response;
import java.util.Date;

public class ErrorResponse {
    private final HttpStatus status;
    private final String message;
    private final ErrorCode errorCode;
    private final Date timestamp;

    protected ErrorResponse(final String message, final ErrorCode errorCode, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = new java.util.Date();
    }

    public static ErrorResponse of(final String message, final ErrorCode errorCode) {
        return new ErrorResponse(message, errorCode, HttpStatus.valueOf(errorCode.getStatus().getStatusCode()));
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
