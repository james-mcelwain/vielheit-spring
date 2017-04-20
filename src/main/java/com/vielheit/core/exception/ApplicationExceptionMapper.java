package com.vielheit.core.exception;

import com.vielheit.core.controller.ControllerContext;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * jcm - 4/19/17.
 */
@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException>, ControllerContext {

    @Override
    public Response toResponse(ApplicationException e) {
        return error(ErrorResponse.of(e.getMessage(), e.getErrorCode()));
    }
}
