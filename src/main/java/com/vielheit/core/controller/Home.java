package com.vielheit.core.controller;

import com.vielheit.core.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("/")
public class Home {
    @Inject
    UserRepository userRepository;

    @GET
    public String homeCtrl() {
        return "ok";
    }
}
