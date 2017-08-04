package com.vielheit.graph.controller;

import com.vielheit.core.controller.ControllerContext;
import com.vielheit.graph.service.EntryService;
import com.vielheit.core.utility.OptionalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Controller
@Path("api/journal")
public class JournalController implements ControllerContext, OptionalResponse {
    @Autowired
    EntryService entryService;

    @Path("{userId}")
    @GET
    public Response getEntries(@PathParam("userId") Long userId) {
        return null;
    }
}
