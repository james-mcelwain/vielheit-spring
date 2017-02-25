package com.vielheit.core.controller;

import com.vielheit.core.service.Loggable;
import com.vielheit.core.service.UserService;
import com.vielheit.core.utility.OptionalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Controller
@Path("/api/users")
public class UserController implements OptionalResponse {
    @Autowired
    private UserService userService;

    @GET
    @Path("{id}")
    public Response getUserById(@PathParam("id") Long id) {
        return okIfPresent(userService.getById(id));
    }


    @GET
    @Path("email/{email}")
    public Response getUserByEmail(@PathParam("email") String email) {
        return okIfPresent(userService.getByEmailAddress(email));
    }

}
