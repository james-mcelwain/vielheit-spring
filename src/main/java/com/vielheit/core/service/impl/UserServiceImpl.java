package com.vielheit.core.service.impl;

import com.vielheit.app.service.GraphUserService;
import com.vielheit.core.domain.Role;
import com.vielheit.core.domain.User;
import com.vielheit.core.domain.UserRole;
import com.vielheit.core.repository.UserRepository;
import com.vielheit.core.repository.UserRoleRepository;
import com.vielheit.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
   private UserRepository userRepository;
   private UserRoleRepository userRoleRepository;
   private GraphUserService graphUserService;
   private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, GraphUserService graphUserService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.graphUserService = graphUserService;
    }

    @Override
    public Optional<User> getById(Long id) {
        return one(() -> userRepository.findOne(id));
    }

    @Override
    public Optional<User> saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);

        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);
        userRole.setId(new UserRole.Id(user.getId(), Role.USER));
        userRoleRepository.save(userRole);
        user.setRoles(new ArrayList<>(Collections.singletonList(userRole)));
        graphUserService.saveUser(user);
        return one(() -> userRepository.save(user));
    }

    @Override
    public Optional<User> getByEmailAddress(String emailAddress) {
        return oneOrNone(() -> userRepository.findByEmailAddress(emailAddress));
    }
}
