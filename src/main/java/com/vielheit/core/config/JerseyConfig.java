package com.vielheit.core.config;


import com.vielheit.core.security.controller.TokenController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(TokenController.class);
    }
}
