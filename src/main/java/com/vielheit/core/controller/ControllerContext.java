package com.vielheit.core.controller;

import com.vielheit.core.domain.User;
import com.vielheit.security.auth.JwtAuthenticationToken;
import com.vielheit.security.model.UserContext;

import javax.ws.rs.core.SecurityContext;

public interface ControllerContext {
    default UserContext getUserContext(SecurityContext ctx) {
        return (UserContext) ((JwtAuthenticationToken) ctx.getUserPrincipal()).getPrincipal();
    }

    default boolean isResourceOwner(Long userId, SecurityContext ctx) {
        UserContext userContext = getUserContext(ctx);
        return userId.equals(userContext.getUserId());
    }
}
