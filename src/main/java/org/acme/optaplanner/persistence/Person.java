package org.acme.optaplanner.persistence;

import java.time.LocalDate;
import java.util.List;


public class Person {
    private String name;
    private List<Role> roles;
    private List<LocalDate> unavailableDates;

    // Standaard constructor voor JSON en OptaPlanner
    public Person() {}
    public Person(String name, List<Role> roles, List<LocalDate> unavailableDates) {
        this.name = name;
        this.roles = roles;
        this.unavailableDates = unavailableDates;
    }
    public String getName() { return name; }
    public List<Role> getRoles() { return roles; }
    public List<LocalDate> getUnavailableDates() { return unavailableDates; }
}
