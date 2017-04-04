package com.vielheit.app.controller;

import com.vielheit.app.domain.Entry;
import com.vielheit.app.service.EntryService;
import com.vielheit.core.controller.ControllerContext;
import com.vielheit.core.utility.OptionalResponse;
import com.vielheit.security.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Controller
@Path("/api/entry")
public class EntryController implements OptionalResponse, ControllerContext {
    @Autowired
    EntryService entryService;

    @GET
    @Path("{id}")
    public Entry getEntryById(@PathParam("id") Long id) {
        return null;
    }

    @GET
    @Path("user/{user-id}")
    @UserResource
    public Response getEntries(@PathParam("user-id") Long userId) {
        return okIfPresent(entryService.getEntries(userId));
    }

    @POST
    public Response createNewEntry(Entry entry) {
        if (!isResourceOwner(entry.getUser().getUserId())) return unauthorized();

        return okIfPresent(entryService.saveEntry(entry));
    }
}
