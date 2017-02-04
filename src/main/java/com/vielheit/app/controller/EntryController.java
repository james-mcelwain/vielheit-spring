package com.vielheit.app.controller;

import com.vielheit.app.domain.Entry;
import com.vielheit.app.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Controller
@Path("/api/entry")
public class EntryController {
    @Autowired
    EntryRepository entryRepository;

    @GET
    @Path("{id}")
    public Entry getEntryById(@PathParam("id") Long id) {
        return entryRepository.findOne(id);
    }

    @POST
    public Entry createNewEntry(Entry entry) {
        return entryRepository.save(entry);
    }
}
