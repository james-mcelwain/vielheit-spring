package com.vielheit.core.controller;

import com.vielheit.core.domain.AbstractionType;
import com.vielheit.core.service.AbstractionTypeService;
import com.vielheit.core.utility.OptionalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Controller
@Path("api/abstraction")
public class AbstractionTypeController implements OptionalResponse, ControllerContext {
    @Autowired
    AbstractionTypeService abstractionTypeService;

    @Path("type")
    @POST
    public Response postAbstractionType(AbstractionType abstractionType) {
        if (!isResourceOwner(abstractionType.getId().getUserId())) return unauthorized();

        return okIfPresent(abstractionTypeService.saveAbstractionType(abstractionType));
    }

    @Path("type")
    @GET
    public Response getAbstractionTypes() {
        Optional abstractionTypes = abstractionTypeService.findByUserId(userId());
        return okIfPresent(abstractionTypes);
    }
}
