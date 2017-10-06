package com.vielheit.core.service.impl;

import com.vielheit.core.exception.ApplicationException;
import com.vielheit.core.exception.IllegalRequestException;
import com.vielheit.core.domain.Role;
import com.vielheit.core.domain.User;
import com.vielheit.core.domain.UserRole;
import com.vielheit.core.repository.UserRepository;
import com.vielheit.core.repository.UserRoleRepository;
import com.vielheit.core.service.UserService;
import com.vielheit.graph.domain.GraphUser;
import com.vielheit.graph.service.GraphUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;

@Component
@Cacheable("users")
public class UserServiceImpl implements UserService {
   private UserRepository userRepository;
   private UserRoleRepository userRoleRepository;
   private GraphUserService graphUserService;
   private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Inject
    public UserServiceImpl(
            @NotNull UserRepository userRepository,
            @NotNull UserRoleRepository userRoleRepository,
            @NotNull GraphUserService graphUserService) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.userRoleRepository = Objects.requireNonNull(userRoleRepository);
        this.graphUserService = Objects.requireNonNull(graphUserService);
    }

    @Override
    public Optional<User> getById(Long id) throws ApplicationException {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> updateUser(Long id, User user) throws ApplicationException {
        if (!id.equals(user.getId())) {
            throw new BadRequestException();
        }

        Optional<User> foundUser = getById(id);
        if (!foundUser.isPresent()) {
            throw new BadRequestException();
        }

        User u = foundUser.get();

        u.setEmailAddress(user.getEmailAddress());
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());

        return Optional.ofNullable(userRepository.save(u));
    }

    @Override
    public Optional<User> create(User user) throws ApplicationException {
        if (nonNull(userRepository.findByEmailAddress(user.getEmailAddress()))) {
            throw new IllegalRequestException();
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);

        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);
        userRole.setId(new UserRole.Id(user.getId(), Role.USER));
        userRoleRepository.save(userRole);
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        graphUserService.create(user);
        return Optional.ofNullable(userRepository.save(user));
    }

    @Override
    public Optional<User> getByEmailAddress(String emailAddress) throws ApplicationException {
        return userRepository.findByEmailAddress(emailAddress);
    }

    /**
     * FOR DEV ONLY
     */
    @PostConstruct
    public void createAdminUser() {
        getLogger().info("Creating admin user if not exists...");

        User user = userRepository.findByEmailAddress("admin@vielhe.it").get();
        GraphUser graphUser = graphUserService.find(user);
        if (isNull(graphUser)) {
            getLogger().info("Did not find admin user, creating...");
            graphUserService.create(user);
        }
    }
}
