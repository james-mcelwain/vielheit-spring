package com.vielheit.graph.service;

import com.vielheit.core.domain.User;
import com.vielheit.graph.domain.GraphUser;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GraphUserService {
    GraphUser create(User user);
    GraphUser find(User user);
}