package com.vielheit.core.controller;

import com.vielheit.core.domain.User;
import com.vielheit.core.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Path("/user")
public class UserController {

    @Inject
    UserRepository userRepository;

    @POST
    public String addUser() {
        User user = new User();
        user.setActive(true);
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setEmailAddress("admin@viel.heit");
        userRepository.save(user);
        return "Ok";
    }
    @GET
    public String findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}
