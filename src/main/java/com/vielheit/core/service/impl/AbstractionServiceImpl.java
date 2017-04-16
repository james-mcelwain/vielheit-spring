package com.vielheit.core.service.impl;

import com.vielheit.core.domain.Abstraction;
import com.vielheit.core.repository.AbstractionRepository;
import com.vielheit.core.service.AbstractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * jcm - 4/16/17.
 */
@Component
public class AbstractionServiceImpl implements AbstractionService {
    @Autowired
    AbstractionRepository abstractionRepository;

    @Override
    public Optional<Abstraction> saveAbstraction(Abstraction abstraction) {
        return one(() -> abstractionRepository.save(abstraction));
    }
}
