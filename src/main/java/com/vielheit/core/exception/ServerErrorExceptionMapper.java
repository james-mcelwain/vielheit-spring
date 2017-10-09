package com.vielheit.core.exception;

import org.glassfish.jersey.spi.ExtendedExceptionMapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServerErrorExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception ex) {
        ErrorResponse errorResponse = createErrorResponse(ex);

        return Response.status(errorResponse.getStatus())
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private ErrorResponse createErrorResponse(Exception ex) {
        if (ex instanceof WebApplicationException) {
            WebApplicationException exception = (WebApplicationException) ex;
            return ErrorResponse.of(exception.getLocalizedMessage(), ErrorCode.INTERNAL_ERROR);
        } else {
            return ErrorResponse.of("", ErrorCode.INTERNAL_ERROR);
        }
    }
}
