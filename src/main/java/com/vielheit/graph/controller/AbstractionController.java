package com.vielheit.graph.controller;

import com.vielheit.core.controller.ControllerContext;
import com.vielheit.core.exception.ApplicationException;
import com.vielheit.core.utility.OptionalResponse;
import com.vielheit.graph.domain.Abstraction;
import com.vielheit.graph.domain.AbstractionType;
import com.vielheit.graph.service.AbstractionService;
import com.vielheit.graph.service.AbstractionTypeService;
import com.vielheit.security.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Controller
@Path("api/{user-id}/abstraction")
public class AbstractionController implements OptionalResponse, ControllerContext {
    @Autowired
    AbstractionTypeService abstractionTypeService;
    @Autowired
    AbstractionService abstractionService;

    @UserResource
    @POST
    public Response postAbstraction(Abstraction abstraction) throws ApplicationException {
        return okIfPersisted(abstractionService.create(abstraction));
    }

    @UserResource
    @Path("type")
    @POST
    public Response postAbstractionType(AbstractionType abstractionType) {
        return okIfPersisted(abstractionTypeService.create(abstractionType));
    }

    @UserResource
    @Path("type")
    @GET
    public Response getAbstractionTypes() {
        return okIfPresent(abstractionTypeService.findAll());
    }
}
