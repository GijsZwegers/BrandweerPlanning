package org.acme.optaplanner.domain;

import org.acme.optaplanner.persistence.Persoon;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.time.LocalDate;
import java.util.List;

/**
 * De Planning klasse bevat alle input (personen, diensten) en de oplossingsscore.
 */
@PlanningSolution
public class Planning {

    @ValueRangeProvider(id = "persoonRange")
    @ProblemFactCollectionProperty
    private List<Persoon> persoonList;

    @PlanningEntityCollectionProperty
    private List<Dienst> dienstList;

    @PlanningScore
    private HardSoftScore score;

    public Planning() {}
    public Planning(List<Persoon> persoonList, List<Dienst> dienstList) {
        this.persoonList = persoonList;
        this.dienstList = dienstList;
    }
    public List<Persoon> getPersoonList() { return persoonList; }
    public List<Dienst> getDienstList() { return dienstList; }
    public HardSoftScore getScore() { return score; }
}
