package org.acme.optaplanner.persistence;

import java.time.LocalDate;

public class HistorischeToewijzing {
    private Person persoon;
    private Role rol;
    private LocalDate datum;

    public HistorischeToewijzing() {}
    public HistorischeToewijzing(Person persoon, Role rol, LocalDate datum) {
        this.persoon = persoon;
        this.rol = rol;
        this.datum = datum;
    }

    public Person getPersoon() { return persoon; }
    public Role getRol() { return rol; }
    public LocalDate getDatum() { return datum; }
}
