package com.vielheit.core.controller;

import com.vielheit.core.domain.User;
import com.vielheit.core.exception.ApplicationException;
import com.vielheit.core.service.UserService;
import com.vielheit.core.utility.OptionalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Controller
@Path("/api/auth/register")
public class RegisterController implements OptionalResponse {
    private UserService userService;

    @Inject
    public RegisterController(@NotNull  UserService userService) {
        this.userService = Objects.requireNonNull(userService);
    }

    @POST
    public User registerUser(@NotNull @Valid User user) throws ApplicationException {
        return userService.create(user).orElseThrow(BadRequestException::new);
    }
}
