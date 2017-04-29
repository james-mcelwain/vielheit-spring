package com.vielheit.core.service;

import com.vielheit.core.exception.NoneEntityException;
import com.vielheit.core.exception.OneEntityException;
import com.vielheit.security.auth.JwtAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface Service extends Loggable {
    default <T> Optional<T> oneOrNone(final Supplier<List<T>> supplier) {
        List<T> t = supplier.get();
        if (t == null) {
            return Optional.empty();
        }

        if (t.size() != 1) {
            getLogger().error("Expected 1, found " + t.size() + "!");
        }

        return Optional.of(t.get(0));
    }

    default void none(final Supplier supplier) {
        Object object = supplier.get();
        if (object != null) {
            getLogger().error("Expected not to find entity!");
        }
    }

    default <T> Optional<List<T>> any(final Supplier<List<T>> supplier) {
        return Optional.ofNullable(supplier.get());
    }

    default <T> Optional<T> one(final Supplier<T> supplier) {
        T t = supplier.get();

        if (t == null) {
            getLogger().error("Expected 1 entity!");
        }

        return Optional.ofNullable(t);
    }

    default boolean isResourceOwner(Long id) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return jwt.getUserContext().getUserId().equals(id);
    }

    default long userId() {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return jwt.getUserContext().getUserId();
    }
}
