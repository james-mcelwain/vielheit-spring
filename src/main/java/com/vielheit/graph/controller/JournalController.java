package com.vielheit.graph.controller;

import com.vielheit.core.controller.ControllerContext;
import com.vielheit.core.utility.OptionalResponse;
import com.vielheit.graph.service.JournalService;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Controller
@Path("api/journal")
public class JournalController implements ControllerContext, OptionalResponse {
    private JournalService journalService;

    @Inject
    public JournalController(@NotNull JournalService journalService) {
        this.journalService = Objects.requireNonNull(journalService);
    }

    @GET
    public Response getEntries() {
        return Response.ok(journalService.entries()).build();
    }
}
