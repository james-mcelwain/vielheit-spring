package com.vielheit.core.exception;

public class FakeInternalServerErrorException extends ApplicationException {
    public FakeInternalServerErrorException() {
        super("", ErrorCode.INTERNAL_ERROR);
    }
}
