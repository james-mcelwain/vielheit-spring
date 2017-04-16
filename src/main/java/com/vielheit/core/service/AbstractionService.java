package com.vielheit.core.service;

import com.vielheit.core.domain.Abstraction;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * jcm - 4/16/17.
 */
@Transactional
public interface AbstractionService extends Service {
    Optional<Abstraction> saveAbstraction(Abstraction abstraction);
}
