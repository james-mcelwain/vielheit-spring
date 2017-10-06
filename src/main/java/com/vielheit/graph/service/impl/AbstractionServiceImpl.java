package com.vielheit.graph.service.impl;

import com.vielheit.graph.domain.Abstraction;
import com.vielheit.graph.repository.AbstractionRepository;
import com.vielheit.graph.service.AbstractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;

/**
 * jcm - 4/16/17.
 */
@Component
public class AbstractionServiceImpl implements AbstractionService {
    private AbstractionRepository abstractionRepository;

    @Inject
    public AbstractionServiceImpl(@NotNull AbstractionRepository ar) {
        this.abstractionRepository = Objects.requireNonNull(ar);
    }

    @Override
    public Optional<Abstraction> create(Abstraction abstraction) {
        return Optional.ofNullable(abstractionRepository.save(abstraction));
    }
}
