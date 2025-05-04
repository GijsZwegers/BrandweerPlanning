package org.acme.optaplanner.persistence;

import java.time.LocalDate;
import java.util.List;


public class Persoon {
    private String naam;
    private List<Rol> roles;
    private List<LocalDate> unavailableDates;

    // Standaard constructor voor JSON en OptaPlanner
    public Persoon() {}
    public Persoon(String naam, List<Rol> roles, List<LocalDate> unavailableDates) {
        this.naam = naam;
        this.roles = roles;
        this.unavailableDates = unavailableDates;
    }
    public String getNaam() { return naam; }
    public List<Rol> getRoles() { return roles; }
    public List<LocalDate> getUnavailableDates() { return unavailableDates; }
}
