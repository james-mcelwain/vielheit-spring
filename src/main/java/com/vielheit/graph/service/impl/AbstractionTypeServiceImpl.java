package com.vielheit.graph.service.impl;

import com.vielheit.graph.domain.AbstractionType;
import com.vielheit.graph.domain.GraphUser;
import com.vielheit.graph.repository.AbstractionTypeRepository;
import com.vielheit.graph.repository.GraphUserRepository;
import com.vielheit.graph.service.AbstractionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * jcm - 4/15/17.
 */
@Component
public class AbstractionTypeServiceImpl implements AbstractionTypeService {
    private AbstractionTypeRepository repository;
    private GraphUserRepository userRepository;

    @Inject
    public AbstractionTypeServiceImpl(
            @NotNull AbstractionTypeRepository abstractionTypeRepository,
            @NotNull GraphUserRepository graphUserRepository
    ) {
        this.repository = Objects.requireNonNull(abstractionTypeRepository);
        this.userRepository = Objects.requireNonNull(graphUserRepository);
    }

    @Override
    public Optional<AbstractionType> create(AbstractionType abstractionType) {
        GraphUser user = userRepository.findByUserId(userId());
        if (nonNull(user)) {
            abstractionType.setUser(user);
            return Optional.ofNullable(repository.save(abstractionType));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<AbstractionType>> findAll() {
        GraphUser user = userRepository.findByUserId(userId());
        return Optional.ofNullable(user.getAbstractionTypes());
    }
}
