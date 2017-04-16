package com.vielheit.core.service;

import com.vielheit.core.domain.EntityType;
import org.springframework.data.neo4j.annotation.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * jcm - 4/15/17.
 */
@Transactional
public interface EntityTypeService extends Service {
    Optional<EntityType> saveEntityType(EntityType entityType);

    Optional<List<EntityType>> findEntityTypesForUser();
}
