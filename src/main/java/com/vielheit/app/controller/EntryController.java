package com.vielheit.app.controller;

import com.vielheit.app.domain.Entry;
import com.vielheit.app.repository.EntryRepository;
import com.vielheit.core.controller.ControllerContext;
import com.vielheit.security.auth.JwtAuthenticationToken;
import com.vielheit.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Controller
@Path("/api/entry")
public class EntryController implements ControllerContext {
    @Autowired
    EntryRepository entryRepository;

    @GET
    @Path("{id}")
    public Entry getEntryById(@PathParam("id") Long id, @Context SecurityContext ctx) {
        return entryRepository.findOne(id);
    }

    @POST
    public Entry createNewEntry(Entry entry) {
        return entryRepository.save(entry);
    }
}
