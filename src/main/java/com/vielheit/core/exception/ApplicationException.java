package com.vielheit.core.exception;

/**
 * jcm - 4/19/17.
 */
public abstract class ApplicationException extends Exception {
    private ErrorCode errorCode;

    protected ApplicationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
