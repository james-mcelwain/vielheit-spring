package com.vielheit.core.config;


import com.vielheit.core.controller.AbstractionController;
import com.vielheit.core.controller.RegisterController;
import com.vielheit.core.controller.UserController;
import com.vielheit.security.UserResourceFilter;
import com.vielheit.security.controller.TokenController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        registerEndpoints();
        register(ValidationExceptionMapper.class);
    }

    private void registerEndpoints() {
        register(UserResourceFilter.class);

        register(TokenController.class);
        register(UserController.class);
        register(RegisterController.class);
        register(AbstractionController.class);

    }
}
