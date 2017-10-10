package com.vielheit.core.exception;

public class BadRequestException extends ApplicationException {
    public BadRequestException(String message) {
        super(message, ErrorCode.BAD_REQUEST);
    }
}
