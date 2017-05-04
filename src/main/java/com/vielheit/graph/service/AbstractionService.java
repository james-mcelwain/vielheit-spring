package com.vielheit.graph.service;

import com.vielheit.core.service.Service;
import com.vielheit.graph.domain.Abstraction;

import javax.transaction.Transactional;

/**
 * jcm - 4/16/17.
 */
@Transactional
public interface AbstractionService extends Service {
    Abstraction create(Abstraction abstraction);
}
