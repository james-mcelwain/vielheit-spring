package com.vielheit.core.service.impl;

import com.vielheit.core.domain.Abstraction;
import com.vielheit.core.domain.AbstractionType;
import com.vielheit.core.domain.User;
import com.vielheit.core.exception.EntityAlreadyExistsException;
import com.vielheit.core.repository.AbstractionRepository;
import com.vielheit.core.repository.AbstractionTypeRepository;
import com.vielheit.core.repository.UserRepository;
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
    @Autowired
    AbstractionTypeRepository abstractionTypeRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<Abstraction> saveAbstraction(Abstraction abstraction) throws EntityAlreadyExistsException {
        if (!abstractionRepository.findByNameAndUserId(abstraction.getName(), userId()).isEmpty()) {
            throw new EntityAlreadyExistsException("Abstraction " + abstraction.getName() + " already exists!");
        }

        User user = userRepository.findOne(userId());
        AbstractionType abstractionType = abstractionTypeRepository.findOne(abstraction.getAbstractionType().getId());

        abstraction.setAbstractionType(abstractionType);
        abstraction.setUser(user);
        abstractionType.getAbstractions().add(abstraction);
        abstractionTypeRepository.save(abstractionType);
        return one(() -> abstractionRepository.save(abstraction));
    }
}
