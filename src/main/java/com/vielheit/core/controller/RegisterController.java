package com.vielheit.core.controller;

import com.vielheit.core.domain.User;
import com.vielheit.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Controller
@Path("/api/auth/register")
public class RegisterController{
    @Autowired UserService userService;

    @POST
    public Response registerUser(RegisterUserRequest req) {
        User user = new User();
        user.setActive(true);
        user.setEmailAddress(req.getEmailAddress());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setPassword(req.getPassword());

        userService.saveUser(user);

        return Response.ok().build();
    }

    private static class RegisterUserRequest {
        private String firstName;
        private String lastName;
        private String emailAddress;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        private String password;
    }
}
