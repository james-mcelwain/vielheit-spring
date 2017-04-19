package com.vielheit.core.exception;

/**
 * jcm - 4/16/17.
 */
public class EntityAlreadyExistsException extends ApplicationException {
    public EntityAlreadyExistsException(String name) {
        super(name + "already exists!", ErrorCode.ENTITY_EXISTS);
    }
}
