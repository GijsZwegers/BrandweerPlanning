package org.acme.optaplanner.domain;

import org.acme.optaplanner.persistence.Person;
import org.acme.optaplanner.persistence.Role;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.time.LocalDate;

@PlanningEntity
public class Dienst {
    @PlanningId
    private Long id;
    private Role role;
    private LocalDate datum;

    // Dit is de planningvariabele: welke Persoon wordt toegewezen?
    @PlanningVariable(valueRangeProviderRefs = "persoonRange")
    private Person person;

    public Dienst() {}
    public Dienst(Long id, Role role, LocalDate datum) {
        this.id = id;
        this.role = role;
        this.datum = datum;
    }
    public Long getId() { return id; }
    public Role getRol() { return role; }
    public LocalDate getDatum() { return datum; }
    public Person getPersoon() { return person; }
    public void setPersoon(Person person) { this.person = person; }
}