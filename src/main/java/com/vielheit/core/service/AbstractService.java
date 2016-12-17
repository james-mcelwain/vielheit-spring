package com.vielheit.core.service;

import com.vielheit.core.utility.Retriever;
import org.apache.log4j.Logger;

import javax.persistence.NoResultException;
import java.util.Optional;

public abstract class AbstractService {
    Logger log = Logger.getLogger(AbstractService.class);

    public <T> Optional<T> oneOrNone(final Retriever<T> retriever) {
        try {
            return Optional.of(retriever.retrieve());
        } catch (Exception e) {
            log.error(e);
        }

        return Optional.empty();
    }
}
