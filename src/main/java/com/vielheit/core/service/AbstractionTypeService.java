package com.vielheit.core.service;

import com.vielheit.core.domain.AbstractionType;
import org.springframework.data.neo4j.annotation.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * jcm - 4/15/17.
 */
@Transactional
public interface AbstractionTypeService extends Service {
    Optional<AbstractionType> saveAbstractionType(AbstractionType abstractionType);

    Optional<List<AbstractionType>> findByUserId(Long userId);
}
