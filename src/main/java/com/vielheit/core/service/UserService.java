package com.vielheit.core.service;

import com.vielheit.core.domain.Role;
import com.vielheit.core.domain.User;
import com.vielheit.core.domain.UserRole;
import com.vielheit.core.repository.UserRepository;
import com.vielheit.core.repository.UserRoleRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

@Component
public class UserService extends AbstractService {
   private UserRepository userRepository;
   private UserRoleRepository userRoleRepository;

    @Inject
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;

        User user = new User();
        user.setActive(true);
        user.setEmailAddress("admin@vielhe.it");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setPassword("$2a$10$bnC26zz//2cavYoSCrlHdecWF8tkGfPodlHcYwlACBBwJvcEf0p2G");

        user = userRepository.save(user);
        UserRole userRole = new UserRole();
        userRole.setRole(Role.ADMIN);
        userRole.setId(new UserRole.Id(user.getId(), Role.ADMIN));
        userRoleRepository.save(userRole);
        user.setRoles(Collections.singletonList(userRole));
        userRepository.save(user);
    }

    public Optional<User> getById(Long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    public boolean saveUser(User user) {
        user = userRepository.save(user);
        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);
        userRole.setId(new UserRole.Id(user.getId(), Role.USER));
        userRoleRepository.save(userRole);
        user.setRoles(Collections.singletonList(userRole));
        userRepository.save(user);

        return true;
    }

    public Optional<User> getByEmailAddress(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress)
                .stream()
                .findFirst();
    }
}
