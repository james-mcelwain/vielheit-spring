package com.vielheit.core.service;

import com.vielheit.core.exception.UnexpectedResultException;
import com.vielheit.security.auth.JwtAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface Service extends Loggable {
    default void none(final Supplier supplier) throws UnexpectedResultException {
        Object object = supplier.get();
        if (object != null) {
            getLogger().error("Expected none: " + object.toString());
            throw new UnexpectedResultException();
        }
    }

    default <T> Optional<T> one(final Supplier<T> supplier) throws UnexpectedResultException {
        T t = supplier.get();

        if (t == null) {
            getLogger().error("Expected one: " + supplier.toString());
            throw new UnexpectedResultException();
        }

        return Optional.ofNullable(t);
    }

    default boolean isResourceOwner(Long id) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return jwt.getUserContext().getUserId().equals(id);
    }

    default Long userId() {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return jwt.getUserContext().getUserId();
    }
}
