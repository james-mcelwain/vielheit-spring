package com.vielheit.core.controller;

import com.vielheit.core.exception.ErrorCode;
import com.vielheit.core.exception.ErrorResponse;
import com.vielheit.core.domain.Abstraction;
import com.vielheit.core.domain.AbstractionType;
import com.vielheit.core.exception.EntityAlreadyExistsException;
import com.vielheit.core.service.AbstractionService;
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
public class AbstractionController implements OptionalResponse, ControllerContext {
    @Autowired
    AbstractionTypeService abstractionTypeService;
    @Autowired
    AbstractionService abstractionService;

    @POST
    public Response postAbstraction(Abstraction abstraction) {
        try {
            return okIfPresent(abstractionService.saveAbstraction(abstraction));
        } catch (EntityAlreadyExistsException eaeex) {
            return error(ErrorResponse.of(eaeex.getMessage(), ErrorCode.ENTITY_EXISTS, Response.Status.BAD_REQUEST));
        }
    }

    @Path("type")
    @POST
    public Response postAbstractionType(AbstractionType abstractionType) {
        return okIfPresent(abstractionTypeService.saveAbstractionType(abstractionType));
    }

    @Path("type")
    @GET
    public Response getAbstractionTypes() {
        Optional abstractionTypes = abstractionTypeService.findByUserId(userId());
        return okIfPresent(abstractionTypes);
    }
}
