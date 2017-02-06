package com.vielheit.core.controller;

import com.vielheit.core.domain.User;
import com.vielheit.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Controller
@Path("/api/auth/register")
public class RegisterController{
    @Autowired
    UserService userService;

    @POST
    public Response registerUser(@NotNull @Valid User user) {
        User registeredUser = userService.saveUser(user);
        return Response.ok().entity(registeredUser).build();
    }
}
