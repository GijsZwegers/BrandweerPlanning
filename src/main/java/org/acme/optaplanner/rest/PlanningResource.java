package org.acme.optaplanner.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.optaplanner.domain.Planning;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

@Path("/planning")
@Produces(MediaType.APPLICATION_JSON)
public class PlanningResource {

    @Inject
    PlanningService planningService;

    @GET
    @Path("/{datum}")
    public Planning getPlanningVoorDatum(@PathParam("datum") String datumStr) {
        LocalDate datum = LocalDate.parse(datumStr);
        return planningService.solveForDate(datum);
    }
}