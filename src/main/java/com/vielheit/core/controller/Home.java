package com.vielheit.core.controller;


import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
@Component
public class Home {

    @GET
    public String homeCtrl() {
        return "Hello, world!";
    }
}
