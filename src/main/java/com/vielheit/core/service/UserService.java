package com.vielheit.core.service;

import com.vielheit.core.domain.Role;
import com.vielheit.core.domain.User;
import com.vielheit.core.domain.UserRole;
import com.vielheit.core.repository.UserRepository;
import com.vielheit.core.repository.UserRoleRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Component
@Transactional
@SuppressWarnings("unchecked")
public class UserService implements Service {
   private UserRepository userRepository;
   private UserRoleRepository userRoleRepository;

    @Inject
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public Optional<User> getById(Long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    public User saveUser(User user) {
        user = userRepository.save(user);
        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);
        userRole.setId(new UserRole.Id(user.getId(), Role.USER));
        userRoleRepository.save(userRole);
        user.setRoles(Collections.singletonList(userRole));
        return userRepository.save(user);
    }

    public Optional<User> getByEmailAddress(String emailAddress) {
        return oneOrNone(() -> userRepository.findByEmailAddress(emailAddress));
    }
}
