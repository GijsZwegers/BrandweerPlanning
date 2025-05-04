package org.acme.optaplanner.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.optaplanner.domain.Dienst;
import org.acme.optaplanner.domain.Planning;
import org.acme.optaplanner.persistence.HistorischeToewijzing;
import org.acme.optaplanner.persistence.Person;
import org.acme.optaplanner.persistence.Role;
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
    private List<Person> voorbeeldPersonen() {
        return Arrays.asList(
                new Person("Jan", Arrays.asList(Role.OEFENLEIDER, Role.ASSISTENT_OEFENLEIDER),
                        List.of()),
                new Person("Piet", Arrays.asList(Role.ENSCENEERDER, Role.POMPBEDIENDEN),
                        List.of()),
                new Person("Klaas", Arrays.asList(Role.MANSCHAP_1, Role.MANSCHAP_2, Role.MANSCHAP_3),
                        List.of()),
                new Person("Els", Arrays.asList(Role.MANSCHAP_2, Role.MANSCHAP_4, Role.POMPBEDIENDEN),
                        List.of()),
                new Person("Mieke", Arrays.asList(Role.MANSCHAP_3, Role.MANSCHAP_4, Role.STAGIAIRE),
                        Collections.emptyList()),
        new Person("Mieke", Arrays.asList(Role.MANSCHAP_4, Role.MANSCHAP_4, Role.STAGIAIRE),
                Collections.emptyList()),
        new Person("Mieke", Arrays.asList(Role.POMPBEDIENDEN, Role.MANSCHAP_4, Role.STAGIAIRE),
                Collections.emptyList()),
        new Person("Mieke", Arrays.asList(Role.ENSCENEERDER, Role.MANSCHAP_4, Role.STAGIAIRE),
                Collections.emptyList()),
        new Person("Mieke", Arrays.asList(Role.MANSCHAP_3, Role.MANSCHAP_4, Role.STAGIAIRE),
                Collections.emptyList())
        );
    }

    /**
     * Genereer een Planning voor de gegeven datum en los deze op met OptaPlanner.
     */
    public Planning solveForDate(LocalDate date) {
        List<Person> personen = voorbeeldPersonen();
        // Bepaal de vereiste rollen voor die datum
        List<Role> vereisteRollen = Arrays.asList(
                Role.OEFENLEIDER, Role.ENSCENEERDER, Role.POMPBEDIENDEN,
                Role.MANSCHAP_1, Role.MANSCHAP_2, Role.MANSCHAP_3, Role.MANSCHAP_4
        );
        // Maak één Dienst per vereiste rol
        List<Dienst> diensten = new ArrayList<>();
        long id = 0;
        for (Role role : vereisteRollen) {
            diensten.add(new Dienst(id++, role, date));
        }

        List<HistorischeToewijzing> historiek = Arrays.asList(
                new HistorischeToewijzing(new Person("piet", List.of(), List.of()), Role.POMPBEDIENDEN, LocalDate.of(2025, 4, 28)),
                new HistorischeToewijzing(new Person("klaas", List.of(), List.of()), Role.MANSCHAP_2, LocalDate.of(2025, 4, 25))
        );

        // Bouw het initiële probleem
        Planning problem = new Planning(personen, diensten, historiek);
        // Laat OptaPlanner het probleem oplossen (wacht op resultaat)
        SolverJob<Planning, String> solverJob = solverManager.solve(date.toString(), problem);
        try {
            return solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solver faalde", e);
        }
    }
}