package com.vielheit.core.service;

import com.vielheit.core.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService extends Service {

    @Transactional(value="coreTransactionManager", rollbackFor=Exception.class)
    Optional<User> saveUser(User user);

    Optional<User> getByEmailAddress(String email);

    Optional<User> getById(Long id);
}
