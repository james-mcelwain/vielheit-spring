package com.vielheit.app.service;

import com.vielheit.app.domain.GraphUser;
import com.vielheit.core.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface GraphUserService {
    Optional<GraphUser> saveUser(User user);
}
