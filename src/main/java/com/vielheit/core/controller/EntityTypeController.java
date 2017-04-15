package com.vielheit.core.controller;

import com.vielheit.core.domain.EntityType;
import org.springframework.stereotype.Controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Controller
@Path("api/entity")
public class EntityTypeController {

    @Path("type")
    @POST
    public Response postEntityType(EntityType entityType) {
        return Response.ok(entityType).build();
    }
}
