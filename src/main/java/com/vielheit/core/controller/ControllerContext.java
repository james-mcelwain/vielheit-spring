package com.vielheit.core.controller;

import com.vielheit.core.exception.ErrorCode;
import com.vielheit.core.exception.ErrorResponse;
import com.vielheit.security.auth.JwtAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.core.Response;

public interface ControllerContext {
    default boolean isResourceOwner(Long id) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return jwt.getUserContext().getUserId().equals(id);
    }

    default Long userId() {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return jwt.getUserContext().getUserId();
    }

    default Response error(ErrorResponse errorResponse) {
        return Response.status(errorResponse.getStatus())
                .entity(errorResponse)
                .build();
    }

    default Response unauthorized() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(ErrorResponse.of("Unauthorized", ErrorCode.UNAUTHORIZED))
                .build();
    }
}
