package com.vielheit.graph.service;

import com.vielheit.core.exception.ApplicationException;
import com.vielheit.core.service.Service;
import com.vielheit.graph.domain.Abstraction;
import com.vielheit.graph.domain.AbstractionType;
import com.vielheit.graph.domain.Entry;

import java.util.Optional;
import java.util.Set;

/**
 * jcm - 4/30/17.
 */
public interface JournalService extends Service {
    Optional<Entry> create(Entry entry);
    Optional<AbstractionType> create(AbstractionType type) throws ApplicationException;
    Optional<Abstraction> create(Abstraction abstraction);

    Set<Entry> entries();
    Set<AbstractionType> types();
    Set<Abstraction> abstractions();
}
