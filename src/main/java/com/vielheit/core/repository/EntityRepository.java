package com.vielheit.core.repository;

import com.vielheit.core.domain.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends JpaRepository<EntityType, EntityType.Id> {
}
