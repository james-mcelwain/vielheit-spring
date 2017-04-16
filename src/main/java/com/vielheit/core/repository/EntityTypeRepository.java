package com.vielheit.core.repository;

import com.vielheit.core.domain.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityTypeRepository extends JpaRepository<EntityType, EntityType.Id> {
    List<EntityType> findById(EntityType.Id id);

    List<EntityType> findByIdUserId (Long userId);
}
