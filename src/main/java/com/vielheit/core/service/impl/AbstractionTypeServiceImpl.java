package com.vielheit.core.service.impl;

import com.vielheit.core.domain.AbstractionType;
import com.vielheit.core.domain.User;
import com.vielheit.core.repository.AbstractionTypeRepository;
import com.vielheit.core.repository.UserRepository;
import com.vielheit.core.service.AbstractionTypeService;
import com.vielheit.graph.service.GraphAbstractionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * jcm - 4/15/17.
 */
@Component
public class AbstractionTypeServiceImpl implements AbstractionTypeService {
    @Autowired
    AbstractionTypeRepository abstractionTypeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    GraphAbstractionTypeService graphAbstractionTypeService;

    @Override
    public Optional<AbstractionType> saveAbstractionType(AbstractionType abstractionType) {
        User user = userRepository.findOne(userId());
        abstractionType.setUser(user);
        abstractionType = abstractionTypeRepository.save(abstractionType);
        graphAbstractionTypeService.save(abstractionType);
        return Optional.of(abstractionType);
    }

    @Override
    @CachePut(value = "abstraction-type", key = "#userId")
    public Optional<List<AbstractionType>> findByUserId(Long userId) {
        getLogger().info("OK");
        return any(() -> abstractionTypeRepository.findByIdUserId(userId));
    }
}
