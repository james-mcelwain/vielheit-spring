package com.vielheit.core.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.vielheit.core.domain.User;
import com.vielheit.core.repository.UserRepository;
import com.vielheit.core.utility.KeyReader;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.security.interfaces.RSAPrivateKey;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Path("/user")
public class UserController {
    private Logger log = Logger.getLogger(UserController.class);

    private RSAPrivateKey privateKey;

    public UserController() {
        try {
            privateKey = (RSAPrivateKey) KeyReader.readPrivateKey();
            log.info("Private key loaded");
        } catch(Exception ex) {
            log.error("Could not read private key into memory", ex);
        }
    }

    @Inject
    UserRepository userRepository;

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    public String addUser(User user) {
        userRepository.save(user);
        return "Ok";
    }

    @GET
    @Path("/jwt")
    public String getJwt() {
        String token = "";

        try {
            if (privateKey != null) {
                token = JWT.create()
                        .withIssuer("Vielheit")
                        .sign(Algorithm.RSA256(privateKey));
            }
        } catch(JWTCreationException jwtce) {
            log.error("Could not create JWT", jwtce);
        }

        return token;
    }

    @GET
    public String findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}
