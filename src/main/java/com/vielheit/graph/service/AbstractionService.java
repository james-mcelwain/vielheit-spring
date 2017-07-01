package com.vielheit.graph.service;

import com.vielheit.core.service.Service;
import com.vielheit.graph.domain.Abstraction;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * jcm - 4/16/17.
 */
@Transactional
public interface AbstractionService extends Service {
    Optional<Abstraction> create(Abstraction abstraction);
}
