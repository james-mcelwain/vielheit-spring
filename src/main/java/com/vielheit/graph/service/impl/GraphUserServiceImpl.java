package com.vielheit.graph.service.impl;

import com.vielheit.core.domain.User;
import com.vielheit.graph.domain.GraphUser;
import com.vielheit.graph.repository.GraphUserRepository;
import com.vielheit.graph.service.GraphUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphUserServiceImpl implements GraphUserService {
    @Autowired
    private GraphUserRepository graphUserRepository;

    @Override
    public GraphUser create(User user) {
        GraphUser graphUser = new GraphUser();
        graphUser.setUserId(user.getId());
        return graphUserRepository.save(graphUser);
    }

    @Override
    public GraphUser find(User user) {
        return graphUserRepository.findByUserId(user.getId());
    }
}