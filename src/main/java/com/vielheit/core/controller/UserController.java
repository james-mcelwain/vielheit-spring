package com.vielheit.core.controller;

import com.vielheit.core.domain.User;
import com.vielheit.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Controller
@Path("/api/users")
public class UserController {
    @Autowired UserService userService;

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") Long id) {
        Optional<User> user = userService.getById(id);

        if (user.isPresent()) {
            return Response.ok(user.get()).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
