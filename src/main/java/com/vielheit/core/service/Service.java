package com.vielheit.core.service;

import com.vielheit.security.auth.JwtAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface Service extends Loggable {
    default <T> Optional<T> oneOrNone(final Supplier<List<T>> supplier) {
        try {
            List<T> t = supplier.get();
            if (t == null) {
                throw new NoResultException("Entity not found");
            }

            if (t.size() != 1) {
                throw new NoResultException("Expected 1; Found " + t.size());
            }

            return Optional.of(t.get(0));
        } catch (Exception e) {
            getLogger().error(e);
        }

        return Optional.empty();
    }

    default <T> Optional<List<T>> any(final Supplier<List<T>> supplier) {
        return Optional.ofNullable(supplier.get());
    }

    default <T> Optional<T> one(final Supplier<T> supplier) {
        return Optional.ofNullable(supplier.get());
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
