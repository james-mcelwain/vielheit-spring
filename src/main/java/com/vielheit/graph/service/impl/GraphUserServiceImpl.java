package com.vielheit.graph.service.impl;

import com.vielheit.core.domain.User;
import com.vielheit.graph.domain.GraphUser;
import com.vielheit.graph.repository.GraphUserRepository;
import com.vielheit.graph.service.GraphUserService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
public class GraphUserServiceImpl implements GraphUserService {
    private GraphUserRepository graphUserRepository;

    @Inject
    public GraphUserServiceImpl(@NotNull GraphUserRepository r) {
        this.graphUserRepository = Objects.requireNonNull(r);
    }

    @Override
    public GraphUser create(User user) {
        GraphUser graphUser = new GraphUser();
        graphUser.setUserId(user.getId());
        return graphUserRepository.save(graphUser);
    }

    @Override
    public GraphUser find(Long userId) {
        return graphUserRepository.findByUserId(userId);
    }

    @Override
    public GraphUser find(User user) {
        return graphUserRepository.findByUserId(user.getId());
    }
}