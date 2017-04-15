package com.vielheit.core.controller;

import com.vielheit.core.domain.EntityType;
import com.vielheit.core.service.EntityTypeService;
import com.vielheit.core.utility.OptionalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Controller
@Path("api/entity")
public class EntityTypeController implements OptionalResponse{
    @Autowired
    EntityTypeService entityTypeService;

    @Path("type")
    @POST
    public Response postEntityType(EntityType entityType) {
        return okIfPresent(entityTypeService.saveEntityType(entityType));
    }
}
