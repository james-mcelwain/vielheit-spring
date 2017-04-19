package com.vielheit.core;

import com.vielheit.core.exception.ErrorCode;
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

    public static ErrorResponse of(final String message, final ErrorCode errorCode, HttpStatus status) {
        return new ErrorResponse(message, errorCode, status);
    }

    public static ErrorResponse of(final String message, final ErrorCode errorCode, Response.Status status) {
        return new ErrorResponse(message, errorCode, HttpStatus.valueOf(status.getStatusCode()));
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
