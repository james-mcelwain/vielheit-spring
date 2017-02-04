package com.vielheit.core.service;

import com.sun.org.apache.bcel.internal.generic.RET;
import com.vielheit.core.domain.Role;
import com.vielheit.core.domain.User;
import com.vielheit.core.domain.UserRole;
import com.vielheit.core.repository.UserRepository;
import com.vielheit.core.repository.UserRoleRepository;
import com.vielheit.core.utility.Retriever;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

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

    private <T> Retriever retrieverFactory(Supplier<List<T>> supplier) throws PersistenceException {
        return new Retriever() {
            @Override
            public List<T> retrieve() throws NoResultException {
                return supplier.get();
            }
        };

    }

    public Optional<User> getByEmailAddress(String emailAddress) {
        return oneOrNone(this.retrieverFactory((() -> userRepository.findByEmailAddress(emailAddress))));
    }
}
