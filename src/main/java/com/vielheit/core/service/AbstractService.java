package com.vielheit.core.service;

import com.vielheit.core.utility.Retriever;

import javax.persistence.NoResultException;
import java.util.Optional;

public abstract class AbstractService {

    public <T> Optional<T> oneOrNone(final Retriever<T> retriever) {
        try {
            return Optional.of(retriever.retrieve());
        } catch (NoResultException nse) { }

        return Optional.empty();
    }
}
