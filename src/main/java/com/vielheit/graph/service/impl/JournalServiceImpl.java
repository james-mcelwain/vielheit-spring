package com.vielheit.graph.service.impl;

import com.vielheit.graph.domain.Abstraction;
import com.vielheit.graph.domain.AbstractionType;
import com.vielheit.graph.domain.Entry;
import com.vielheit.graph.repository.AbstractionRepository;
import com.vielheit.graph.repository.AbstractionTypeRepository;
import com.vielheit.graph.repository.EntryRepository;
import com.vielheit.graph.service.JournalService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * jcm - 4/30/17.
 */
@Component
public class JournalServiceImpl implements JournalService {
    private EntryRepository entryRepository;
    private AbstractionTypeRepository typeRepository;
    private AbstractionRepository abstractionRepository;

    @Inject
    public JournalServiceImpl(
            @NotNull EntryRepository entryRepository,
            @NotNull AbstractionTypeRepository typeRepository,
            @NotNull AbstractionRepository abstractionRepository
    ) {
        this.entryRepository = Objects.requireNonNull(entryRepository);
        this.typeRepository = Objects.requireNonNull(typeRepository);
        this.abstractionRepository = Objects.requireNonNull(abstractionRepository);
    }

    @Override
    public Optional<Entry> create(Entry entry) {
        return Optional.ofNullable(entryRepository.save(entry));
    }

    @Override
    public Optional<AbstractionType> create(AbstractionType type) {
        return Optional.ofNullable(typeRepository.save(type));
    }

    @Override
    public Optional<Abstraction> create(Abstraction abstraction) {
        return Optional.ofNullable(abstractionRepository.save(abstraction));
    }

    @Override
    public Set<Entry> entries() {
        return entryRepository.findByUserId(userId());
    }

    @Override
    public Set<AbstractionType> types() {
        return typeRepository.findByUserId(userId());
    }

    @Override
    public Set<Abstraction> abstractions() {
        return abstractionRepository.findByUserId(userId());
    }
}
