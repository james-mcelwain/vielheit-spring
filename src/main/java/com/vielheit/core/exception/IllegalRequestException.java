package com.vielheit.core.exception;

/**
 * jcm - 4/29/17.
 */
public class IllegalRequestException extends ApplicationException {
    public IllegalRequestException() {
        super("Unauthorized", ErrorCode.ILLEGAL_REQUEST);
    }
}
