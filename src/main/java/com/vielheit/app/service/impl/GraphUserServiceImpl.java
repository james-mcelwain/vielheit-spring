package com.vielheit.app.service.impl;

import com.vielheit.app.domain.GraphUser;
import com.vielheit.app.repository.GraphUserRepository;
import com.vielheit.app.service.GraphUserService;
import com.vielheit.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphUserServiceImpl implements GraphUserService {
    @Autowired
    private GraphUserRepository graphUserRepository;

    @Override
    public void saveUser(User user) {
        GraphUser graphUser = new GraphUser();
        graphUser.setUserId(user.getId());
        graphUserRepository.save(graphUser);
    }
}
