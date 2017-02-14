package com.vielheit.app.service;

import com.vielheit.core.domain.User;
import org.springframework.transaction.annotation.Transactional;

public interface GraphUserService {

    @Transactional(value="graphTransactionManager")
    void saveUser(User user);
}
