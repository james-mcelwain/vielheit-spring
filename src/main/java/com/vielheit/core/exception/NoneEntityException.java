package com.vielheit.core.exception;

/**
 * jcm - 4/29/17.
 */
public class NoneEntityException extends ApplicationException {
    public NoneEntityException(String message) {
        super(message, ErrorCode.MORE_THAN_NONE_ENTITY_FOUND);
    }
}
