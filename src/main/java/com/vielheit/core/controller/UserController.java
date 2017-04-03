package com.vielheit.core.controller;

import com.vielheit.security.UserResource;
import com.vielheit.core.domain.User;
import com.vielheit.core.service.UserService;
import com.vielheit.core.utility.OptionalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Controller
@Path("/api/users")
public class UserController implements OptionalResponse {
    @Autowired
    private UserService userService;

    @GET
    @Path("{id}")
    @UserResource
    public Response getUserById(@PathParam("id") Long id) {
        return okIfPresent(userService.getById(id));
    }


    @GET
    @Path("email/{email}")
    @UserResource
    public Response getUserByEmail(@PathParam("email") String email) {
        return okIfPresent(userService.getByEmailAddress(email));
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @UserResource
    public Response updateUserById(@PathParam("id") Long id, User user) {
        return okIfPresent(userService.updateUser(id, user));
    }
}
