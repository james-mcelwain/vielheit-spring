package com.vielheit.graph.controller;

import com.vielheit.core.controller.ControllerContext;
import com.vielheit.core.exception.ApplicationException;
import com.vielheit.core.utility.OptionalResponse;
import com.vielheit.graph.domain.Abstraction;
import com.vielheit.graph.domain.AbstractionType;
import com.vielheit.graph.service.AbstractionService;
import com.vielheit.graph.service.AbstractionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Controller
@Path("api/abstraction")
public class AbstractionController implements OptionalResponse, ControllerContext {
    @Autowired
    AbstractionTypeService abstractionTypeService;
    @Autowired
    AbstractionService abstractionService;

    @POST
    public Response postAbstraction(Abstraction abstraction) throws ApplicationException {
        return Response.ok(abstractionService.create(abstraction)).build();
    }

    @Path("type")
    @POST
    public Response postAbstractionType(AbstractionType abstractionType) {
        return null;
    }

    @Path("type")
    @GET
    public Response getAbstractionTypes() {
        return null;
    }
}
