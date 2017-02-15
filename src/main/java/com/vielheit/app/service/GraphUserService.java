package com.vielheit.app.service;

import com.vielheit.core.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GraphUserService {
    void saveUser(User user);
}
