package com.vielheit.core.controller;

import com.vielheit.core.ErrorCode;
import com.vielheit.core.ErrorResponse;
import com.vielheit.core.domain.User;
import com.vielheit.security.auth.JwtAuthenticationToken;
import com.vielheit.security.model.UserContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

public interface ControllerContext {
    default boolean isResourceOwner(Long id) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return jwt.getUserContext().getUserId().equals(id);
    }

    default Response unauthorized() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(ErrorResponse.of("Unauthorized", ErrorCode.UNAUTHORIZED, Response.Status.UNAUTHORIZED))
                .build();
    }
}
