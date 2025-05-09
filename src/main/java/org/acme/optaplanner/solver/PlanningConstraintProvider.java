package org.acme.optaplanner.solver;

import org.acme.optaplanner.domain.Dienst;
import org.acme.optaplanner.persistence.HistorischeToewijzing;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

import java.time.temporal.ChronoUnit;

/**
 * ConstraintProvider definieert de planningsregels (hard constraints).
 */
public class PlanningConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory) {
        return new Constraint[]{personCannotDoRole(factory), personUnavailable(factory), personUniquePerDate(factory), discourageRecentSameRole(factory)};
    }

    private Constraint personCannotDoRole(ConstraintFactory factory) {
        // Straf als toegewezen persoon de rol niet mag vervullen.
        return factory.forEach(Dienst.class).filter(dienst -> dienst.getPersoon() != null && !dienst.getPersoon().getRoles().contains(dienst.getRol())).penalize(HardSoftScore.ONE_HARD).asConstraint("Person cannot do role");
    }

    private Constraint personUnavailable(ConstraintFactory factory) {
        // Straf als toegewezen persoon niet beschikbaar is op de datum.
        return factory.forEach(Dienst.class).filter(dienst -> dienst.getPersoon() != null && dienst.getPersoon().getUnavailableDates().contains(dienst.getDatum())).penalize(HardSoftScore.ONE_HARD).asConstraint("Person unavailable on date");
    }

    // Optioneel: voorkom dat één persoon twee diensten op dezelfde datum krijgt
    private Constraint personUniquePerDate(ConstraintFactory factory) {
        return factory.forEachUniquePair(Dienst.class, Joiners.equal(Dienst::getPersoon), Joiners.equal(Dienst::getDatum)).penalize(HardSoftScore.ONE_HARD).asConstraint("Person has multiple roles on same date");
    }

    private Constraint discourageRecentSameRole(ConstraintFactory factory) {
        return factory.forEach(Dienst.class).join(HistorischeToewijzing.class, Joiners.equal(Dienst::getRol, HistorischeToewijzing::getRol), Joiners.equal(Dienst::getPersoon, HistorischeToewijzing::getPersoon)).filter((dienst, hist) -> Math.abs(ChronoUnit.DAYS.between(dienst.getDatum(), hist.getDatum())) <= 14).penalize(HardSoftScore.ONE_SOFT).asConstraint("Recent same role used");
    }
}
