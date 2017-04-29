package com.vielheit.core.exception;

/**
 * jcm - 4/29/17.
 */
public class OneEntityException extends ApplicationException {
    public OneEntityException(String name) {
        super(name, ErrorCode.MORE_THAN_ONE_ENTITY_FOUND);
    }
}
