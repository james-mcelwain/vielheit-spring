package com.vielheit.core.service;

import com.vielheit.core.domain.User;
import com.vielheit.core.exception.ApplicationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserService extends Service {

    Optional<User> create(User user) throws ApplicationException;

    Optional<User> getByEmailAddress(String email) throws ApplicationException;

    Optional<User> getById(Long id) throws ApplicationException;

    Optional<User> updateUser(Long id, User user) throws ApplicationException;
}
