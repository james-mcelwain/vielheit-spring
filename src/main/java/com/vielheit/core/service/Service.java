package com.vielheit.core.service;

import com.vielheit.core.exception.UnexpectedResultException;
import com.vielheit.security.auth.JwtAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

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
