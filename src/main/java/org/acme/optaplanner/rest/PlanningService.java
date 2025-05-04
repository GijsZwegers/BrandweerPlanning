package org.acme.optaplanner.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.optaplanner.domain.Dienst;
import org.acme.optaplanner.domain.Planning;
import org.acme.optaplanner.persistence.Persoon;
import org.acme.optaplanner.persistence.Rol;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class PlanningService {

    @Inject
    SolverManager<Planning, String> solverManager;

    // Hardgecodeerde voorbeeldpersonen
    private List<Persoon> voorbeeldPersonen() {
        return Arrays.asList(
                new Persoon("Jan", Arrays.asList(Rol.OEFENLEIDER, Rol.ASSISTENT_OEFENLEIDER),
                        List.of()),
                new Persoon("Piet", Arrays.asList(Rol.ENSCENEERDER, Rol.POMPBEDIENDEN),
                        List.of()),
                new Persoon("Klaas", Arrays.asList(Rol.MANSCHAP_1, Rol.MANSCHAP_2, Rol.MANSCHAP_3),
                        List.of()),
                new Persoon("Els", Arrays.asList(Rol.MANSCHAP_2, Rol.MANSCHAP_4, Rol.POMPBEDIENDEN),
                        List.of()),
                new Persoon("Mieke", Arrays.asList(Rol.MANSCHAP_3, Rol.MANSCHAP_4, Rol.STAGIAIRE),
                        Collections.emptyList()),
        new Persoon("Mieke", Arrays.asList(Rol.MANSCHAP_4, Rol.MANSCHAP_4, Rol.STAGIAIRE),
                Collections.emptyList()),
        new Persoon("Mieke", Arrays.asList(Rol.POMPBEDIENDEN, Rol.MANSCHAP_4, Rol.STAGIAIRE),
                Collections.emptyList()),
        new Persoon("Mieke", Arrays.asList(Rol.ENSCENEERDER, Rol.MANSCHAP_4, Rol.STAGIAIRE),
                Collections.emptyList()),
        new Persoon("Mieke", Arrays.asList(Rol.MANSCHAP_3, Rol.MANSCHAP_4, Rol.STAGIAIRE),
                Collections.emptyList())
        );
    }

    /**
     * Genereer een Planning voor de gegeven datum en los deze op met OptaPlanner.
     */
    public Planning solveForDate(LocalDate date) {
        List<Persoon> personen = voorbeeldPersonen();
        // Bepaal de vereiste rollen voor die datum
        List<Rol> vereisteRollen = Arrays.asList(
                Rol.OEFENLEIDER, Rol.ENSCENEERDER, Rol.POMPBEDIENDEN,
                Rol.MANSCHAP_1, Rol.MANSCHAP_2, Rol.MANSCHAP_3, Rol.MANSCHAP_4
        );
        // Maak één Dienst per vereiste rol
        List<Dienst> diensten = new ArrayList<>();
        long id = 0;
        for (Rol rol : vereisteRollen) {
            diensten.add(new Dienst(id++, rol, date));
        }
        // Bouw het initiële probleem
        Planning problem = new Planning(personen, diensten);
        // Laat OptaPlanner het probleem oplossen (wacht op resultaat)
        SolverJob<Planning, String> solverJob = solverManager.solve(date.toString(), problem);
        try {
            return solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solver faalde", e);
        }
    }
}