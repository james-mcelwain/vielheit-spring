package com.vielheit.core.service;

import com.vielheit.core.domain.User;
import com.vielheit.core.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;

@Component
public class UserService extends AbstractService {
   private UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getByEmailAddress(String emailAddress) {
        return oneOrNone(() ->
                userRepository.findByEmailAddress(emailAddress));
    }
}
