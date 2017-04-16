package com.vielheit.graph.service;

import com.vielheit.core.domain.Abstraction;
import com.vielheit.core.service.Service;
import com.vielheit.graph.domain.GraphAbstraction;

import javax.transaction.Transactional;

/**
 * jcm - 4/15/17.
 */
@Transactional
public interface GraphAbstractionService extends Service {
    GraphAbstraction save(Abstraction abstraction);
}

