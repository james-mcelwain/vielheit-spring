package com.vielheit.core.service;

import com.vielheit.core.domain.User;
import com.vielheit.core.exception.OneEntityException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserService extends Service {

    Optional<User> saveUser(User user);

    Optional<User> getByEmailAddress(String email);

    Optional<User> getById(Long id);

    Optional<User> updateUser(Long id, User user);
}
