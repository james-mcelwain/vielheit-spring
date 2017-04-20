package com.vielheit.core.exception;

import javax.ws.rs.core.Response;

/**
 * jcm - 4/19/17.
 */
public abstract class ApplicationException extends Exception {
    private ErrorCode errorCode;
    private Response.Status status;

    ApplicationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
