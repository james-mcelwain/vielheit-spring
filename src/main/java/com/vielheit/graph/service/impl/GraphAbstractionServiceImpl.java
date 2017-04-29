package com.vielheit.graph.service.impl;

import com.vielheit.core.domain.Abstraction;
import com.vielheit.graph.domain.GraphAbstraction;
import com.vielheit.graph.repository.GraphAbstractionRepository;
import com.vielheit.graph.repository.GraphAbstractionTypeRepository;
import com.vielheit.graph.repository.GraphUserRepository;
import com.vielheit.graph.service.GraphAbstractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * jcm - 4/29/17.
 */
@Component
public class GraphAbstractionServiceImpl implements GraphAbstractionService {
    @Autowired
    GraphAbstractionRepository gaRepository;
    @Autowired
    GraphUserRepository graphUserRepository;
    @Autowired
    GraphAbstractionTypeRepository gatRepository;

    @Override
    public GraphAbstraction create(Abstraction a) {
        GraphAbstraction ga = new GraphAbstraction();
        ga.setName(a.getName());
        ga.setDescription(a.getDescription());
        ga.setGraphAbstractionType(gatRepository.findByAbstractionTypeId(a.getAbstractionType().getId()));
        ga.setUser(graphUserRepository.findByUserId(userId()));
        return gaRepository.save(ga);
    }
}
