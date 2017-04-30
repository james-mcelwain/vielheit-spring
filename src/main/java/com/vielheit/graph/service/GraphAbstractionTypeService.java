package com.vielheit.graph.service;

import com.vielheit.core.domain.AbstractionType;
import com.vielheit.graph.domain.GraphAbstractionType;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * jcm - 4/15/17.
 */
@Transactional
public interface GraphAbstractionTypeService {
    GraphAbstractionType create(AbstractionType abstractionType);

    Optional<List<GraphAbstractionType>> getByUserId(Long userId);
}
