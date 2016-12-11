package com.vielheit.core.controller;

import com.vielheit.core.domain.User;
import com.vielheit.core.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
@Path("/user")
public class UserController {
    @Inject
    UserRepository userRepository;

    @GET
    public String findAll() {
        Stream<User> s = StreamSupport.stream(userRepository.findAll().spliterator(), false);
        String str = "";
        s.map(Object::toString).forEach(x -> {
            str += x + ', ';
        });
    }
}
