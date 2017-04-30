package com.vielheit.core.exception;

/**
 * jcm - 4/29/17.
 */
public class UnexpectedResultException extends ApplicationException {
    public UnexpectedResultException() {
        super("Internal server error", ErrorCode.UNEXPECTED_RESULT);
    }
}
