package com.vielheit.core.service.impl;

import com.vielheit.core.domain.AbstractionType;
import com.vielheit.core.domain.User;
import com.vielheit.core.repository.AbstractionTypeRepository;
import com.vielheit.core.repository.UserRepository;
import com.vielheit.core.service.AbstractionTypeService;
import com.vielheit.graph.service.GraphAbstractionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * jcm - 4/15/17.
 */
@Component
public class AbstractionTypeServiceImpl implements AbstractionTypeService {
    @Autowired
    AbstractionTypeRepository entityTypeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GraphAbstractionTypeService graphAbstractionTypeService;

    @Override
    public Optional<AbstractionType> saveAbstractionType(AbstractionType entityType) {
        User user = userRepository.findOne(userId());
        entityType.setUser(user);
        entityType = entityTypeRepository.save(entityType);
        graphAbstractionTypeService.save(entityType);
        return Optional.of(entityType);
    }

    @Override
    @CachePut(value = "entity-type", key = "#userId")
    public Optional<List<AbstractionType>> findByUserId(Long userId) {
        getLogger().info("OK");
        return any(() -> entityTypeRepository.findByIdUserId(userId));
    }
}
