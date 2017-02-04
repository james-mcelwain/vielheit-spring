package com.vielheit.core.service;

import com.vielheit.core.utility.Retriever;
import org.apache.log4j.Logger;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public interface Service extends Loggable {
    default public <T> Optional<T> oneOrNone(final Retriever<List<T>> retriever) {
        try {
            List<T> t = retriever.retrieve();
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
}
