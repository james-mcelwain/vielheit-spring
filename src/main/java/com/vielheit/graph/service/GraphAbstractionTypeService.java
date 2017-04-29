package com.vielheit.graph.service;

import com.vielheit.core.domain.AbstractionType;
import com.vielheit.graph.domain.GraphAbstractionType;

import javax.transaction.Transactional;

/**
 * jcm - 4/15/17.
 */
@Transactional
public interface GraphAbstractionTypeService {
    GraphAbstractionType create(AbstractionType abstractionType);
}
