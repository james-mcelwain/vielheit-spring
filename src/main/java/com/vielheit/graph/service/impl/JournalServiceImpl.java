package com.vielheit.graph.service.impl;

import com.vielheit.core.exception.ApplicationException;
import com.vielheit.core.exception.BadRequestException;
import com.vielheit.graph.domain.Abstraction;
import com.vielheit.graph.domain.AbstractionType;
import com.vielheit.graph.domain.Entry;
import com.vielheit.graph.domain.GraphUser;
import com.vielheit.graph.repository.AbstractionRepository;
import com.vielheit.graph.repository.AbstractionTypeRepository;
import com.vielheit.graph.repository.EntryRepository;
import com.vielheit.graph.service.GraphUserService;
import com.vielheit.graph.service.JournalService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * jcm - 4/30/17.
 */
@Component
public class JournalServiceImpl implements JournalService {
    private EntryRepository entryRepository;
    private AbstractionTypeRepository typeRepository;
    private AbstractionRepository abstractionRepository;
    private GraphUserService graphUserService;

    @Inject
    public JournalServiceImpl(
            @NotNull EntryRepository entryRepository,
            @NotNull AbstractionTypeRepository typeRepository,
            @NotNull AbstractionRepository abstractionRepository,
            @NotNull GraphUserService graphUserService
    ) {
        this.entryRepository = Objects.requireNonNull(entryRepository);
        this.typeRepository = Objects.requireNonNull(typeRepository);
        this.abstractionRepository = Objects.requireNonNull(abstractionRepository);
        this.graphUserService = Objects.requireNonNull(graphUserService);
    }

    @Override
    public Optional<Entry> create(Entry entry) {
        return Optional.ofNullable(entryRepository.save(entry));
    }

    @Override
    public Optional<AbstractionType> create(AbstractionType type) throws ApplicationException {
        GraphUser user = graphUserService.find(userId());

        if (isNull(type.getId()) && nonNull(typeRepository.findTypeByTypeAndUserId(userId(), type.getType()))) {
            throw new BadRequestException("Abstraction type already exists!");
        }


        type.setUser(user);
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
