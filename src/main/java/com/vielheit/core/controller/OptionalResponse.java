package com.vielheit.core.controller;

import javax.ws.rs.core.Response;
import java.util.Optional;

public interface OptionalResponse {
    default Response okIfPresent(Optional optional) {
        if (optional != null && optional.isPresent()) {
            return Response.ok(optional.get()).build();
        }

        return Response.status(404).build();
    }
}
