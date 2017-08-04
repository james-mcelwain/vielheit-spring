package com.vielheit.graph.service;

import com.vielheit.core.service.Service;
import com.vielheit.graph.domain.AbstractionType;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * jcm - 4/15/17.
 */
@Transactional
public interface AbstractionTypeService extends Service {
    Optional<AbstractionType> create(AbstractionType abstractionType);
    Optional<List<AbstractionType>> findAll();
}
