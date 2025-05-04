package org.acme.optaplanner.domain;

import org.acme.optaplanner.persistence.Persoon;
import org.acme.optaplanner.persistence.Rol;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.time.LocalDate;

@PlanningEntity
public class Dienst {
    @PlanningId
    private Long id;
    private Rol rol;
    private LocalDate datum;

    // Dit is de planningvariabele: welke Persoon wordt toegewezen?
    @PlanningVariable(valueRangeProviderRefs = "persoonRange")
    private Persoon persoon;

    public Dienst() {}
    public Dienst(Long id, Rol rol, LocalDate datum) {
        this.id = id;
        this.rol = rol;
        this.datum = datum;
    }
    public Long getId() { return id; }
    public Rol getRol() { return rol; }
    public LocalDate getDatum() { return datum; }
    public Persoon getPersoon() { return persoon; }
    public void setPersoon(Persoon persoon) { this.persoon = persoon; }
}