package com.vielheit.core.repository;

import com.vielheit.core.domain.AbstractionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbstractionTypeRepository extends JpaRepository<AbstractionType, AbstractionType.Id> {
    List<AbstractionType> findById(AbstractionType.Id abstractionTypeId);

    List<AbstractionType> findByIdUserId(Long userId);
}
