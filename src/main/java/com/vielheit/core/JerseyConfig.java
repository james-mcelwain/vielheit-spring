package com.vielheit.core;


import com.vielheit.core.controller.Home;
import com.vielheit.core.controller.UserController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(Home.class);
        register(UserController.class);
    }
}
