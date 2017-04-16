package com.vielheit.core.service.impl;

import com.vielheit.core.domain.EntityType;
import com.vielheit.core.domain.User;
import com.vielheit.core.repository.EntityTypeRepository;
import com.vielheit.core.repository.UserRepository;
import com.vielheit.core.service.EntityTypeService;
import com.vielheit.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * jcm - 4/15/17.
 */
@Component
@Cacheable("entity-type")
public class EntityTypeServiceImpl implements EntityTypeService {
    @Autowired
    EntityTypeRepository entityTypeRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<EntityType> saveEntityType(EntityType entityType) {
        User user = userRepository.findOne(userId());
        entityType.setUser(user);
        return one(() -> entityTypeRepository.save(entityType));
    }

    @Override
    public Optional<List<EntityType>> findEntityTypesForUser() {
        getLogger().info("OK");
        return any(() -> entityTypeRepository.findByIdUserId(userId()));
    }
}
