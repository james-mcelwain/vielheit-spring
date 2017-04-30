package com.vielheit.core.controller;

import com.vielheit.core.exception.UnexpectedResultException;
import com.vielheit.core.service.EntryService;
import com.vielheit.core.utility.OptionalResponse;
import com.vielheit.graph.service.GraphEntryService;
import com.vielheit.graph.service.impl.GraphEntryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * jcm - 4/30/17.
 */
@Controller
@Path("api/journal")
public class JournalController implements ControllerContext, OptionalResponse {
    @Autowired
    EntryService entryService;

    @Path("{userId}")
    @GET
    public Response getEntries(@PathParam("userId") Long userId) {
        return okIfPresent(entryService.any(() -> entryService.findEntries(userId)));
    }
}
