package com.vielheit.graph.service.impl;

import com.vielheit.core.domain.AbstractionType;
import com.vielheit.core.service.Service;
import com.vielheit.graph.domain.GraphAbstractionType;
import com.vielheit.graph.domain.GraphUser;
import com.vielheit.graph.repository.GraphAbstractionTypeRepository;
import com.vielheit.graph.repository.GraphUserRepository;
import com.vielheit.graph.service.GraphAbstractionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphAbstractionTypeServiceImp implements GraphAbstractionTypeService, Service {
    @Autowired
    GraphAbstractionTypeRepository graphAbstractionTypeRepository;
    @Autowired
    GraphUserRepository graphUserRepository;

    @Override
    public GraphAbstractionType create(AbstractionType at) {
        GraphAbstractionType gat = new GraphAbstractionType();
        gat.setType(at.getId().getType());
        gat.setDescrtiption(at.getDescription());
        gat.setUser(graphUserRepository.findByUserId(userId()));
        gat.setAbstractionTypeId(at.getId());
        return graphAbstractionTypeRepository.save(gat);
    }
}
