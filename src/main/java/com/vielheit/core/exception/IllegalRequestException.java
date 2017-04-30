package com.vielheit.core.exception;

/**
 * jcm - 4/29/17.
 */
public class IllegalRequestException extends ApplicationException {
    public IllegalRequestException() {
        super("Unauotherized", ErrorCode.ILLEGAL_REQUEST);
    }
}
