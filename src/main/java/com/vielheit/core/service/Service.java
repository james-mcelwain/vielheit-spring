package com.vielheit.core.service;

import com.vielheit.security.auth.JwtAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public interface Service extends Loggable {
    default boolean isResourceOwner(Long id) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return jwt.getUserContext().getUserId().equals(id);
    }

    default Long userId() {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return jwt.getUserContext().getUserId();
    }
}
