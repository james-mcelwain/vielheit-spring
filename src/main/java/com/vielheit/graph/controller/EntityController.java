package com.vielheit.graph.controller;

import com.vielheit.core.controller.ControllerContext;
import com.vielheit.core.utility.OptionalResponse;
import com.vielheit.graph.domain.Entity;
import org.springframework.stereotype.Controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Controller
@Path("/api/entity")
public class EntityController implements OptionalResponse, ControllerContext {

    @POST
    public Response creatEntity(Entity entity) {
        if (!isResourceOwner(entity.getUserId())) return unauthorized();

        return Response.status(200).build();
    }
}
