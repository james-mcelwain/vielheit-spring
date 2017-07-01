package com.vielheit.core.utility;

import javax.ws.rs.core.Response;
import java.util.Optional;

public interface OptionalResponse {
    default Response okIfPersisted(Optional optional) {
        if (optional.isPresent()) {
            return Response.ok(optional.get()).build();
        }

        return Response.status(400).build();
    }

    default Response okIfPresent(Optional optional) {
        if (optional.isPresent()) {
            return Response.ok(optional.get()).build();
        }

        return Response.status(404).build();
    }
}
