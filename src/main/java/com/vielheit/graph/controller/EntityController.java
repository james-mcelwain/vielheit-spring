package com.vielheit.app.controller;

import com.vielheit.app.domain.Entry;
import com.vielheit.core.controller.ControllerContext;
import com.vielheit.core.utility.OptionalResponse;
import com.vielheit.security.UserResource;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Controller
@Path("/api/entity")
public class EntityController implements OptionalResponse, ControllerContext {

    @POST
    public Response creatEntity(Entity entity) {

        return okIfPresent(entryService.saveEntry(entry));
    }
}
