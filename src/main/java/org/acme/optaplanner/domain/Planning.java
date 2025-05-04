package org.acme.optaplanner.domain;

import org.acme.optaplanner.persistence.HistorischeToewijzing;
import org.acme.optaplanner.persistence.Person;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

/**
 * De Planning klasse bevat alle input (personen, diensten) en de oplossingsscore.
 */
@PlanningSolution
public class Planning {

    @ValueRangeProvider(id = "persoonRange")
    @ProblemFactCollectionProperty
    private List<Person> people;

    @PlanningEntityCollectionProperty
    private List<Dienst> duties;

    @ProblemFactCollectionProperty
    private List<HistorischeToewijzing> historischeToewijzingList;

    @PlanningScore
    private HardSoftScore score;

    public Planning() {
    }

    public Planning(List<Person> people, List<Dienst> duties, List<HistorischeToewijzing> historischeToewijzingList) {
        this.people = people;
        this.duties = duties;
        this.historischeToewijzingList = historischeToewijzingList;
    }
    public List<Person> getPeople() { return people; }
    public List<Dienst> getDuties() { return duties; }
    public HardSoftScore getScore() { return score; }

    public List<HistorischeToewijzing> getHistorischeToewijzingList() {
        return historischeToewijzingList;
    }
}
