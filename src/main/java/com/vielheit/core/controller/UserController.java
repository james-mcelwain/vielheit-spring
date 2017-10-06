package com.vielheit.core.controller;

import com.vielheit.core.exception.ApplicationException;
import com.vielheit.security.UserResource;
import com.vielheit.core.domain.User;
import com.vielheit.core.service.UserService;
import com.vielheit.core.utility.OptionalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Controller
@Path("/api/users")
public class UserController implements OptionalResponse {
    private UserService userService;

    @Inject
    public UserController(@NotNull UserService userService) {
        userService = Objects.requireNonNull(userService);
    }

    @GET
    @Path("{user-id}")
    @UserResource
    public Response getUserById(@PathParam("user-id") Long id) throws ApplicationException {
        return okIfPresent(userService.getById(id));
    }

    @PUT
    @Path("{user-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @UserResource
    public Response updateUserById(@PathParam("user-id") Long id, User user) throws ApplicationException {
        return okIfPresent(userService.updateUser(id, user));
    }
}
