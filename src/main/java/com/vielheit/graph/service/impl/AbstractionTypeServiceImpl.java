package com.vielheit.graph.service.impl;

import com.vielheit.graph.domain.AbstractionType;
import com.vielheit.graph.domain.GraphUser;
import com.vielheit.graph.repository.AbstractionTypeRepository;
import com.vielheit.graph.repository.GraphUserRepository;
import com.vielheit.graph.service.AbstractionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * jcm - 4/15/17.
 */
@Component
public class AbstractionTypeServiceImpl implements AbstractionTypeService {
    @Autowired
    AbstractionTypeRepository repository;
    @Autowired
    GraphUserRepository userRepository;

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
}
