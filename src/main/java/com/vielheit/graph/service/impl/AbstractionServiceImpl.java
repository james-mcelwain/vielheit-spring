package com.vielheit.graph.service.impl;

import com.vielheit.graph.domain.Abstraction;
import com.vielheit.graph.repository.AbstractionRepository;
import com.vielheit.graph.service.AbstractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * jcm - 4/16/17.
 */
@Component
public class AbstractionServiceImpl implements AbstractionService {
    @Autowired
    AbstractionRepository abstractionRepository;

    @Override
    public Abstraction create(Abstraction abstraction) {
        return abstractionRepository.save(abstraction);
    }
}
