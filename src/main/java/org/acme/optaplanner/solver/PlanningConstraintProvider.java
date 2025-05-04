package org.acme.optaplanner.solver;

import org.acme.optaplanner.domain.Dienst;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

/**
 * ConstraintProvider definieert de planningsregels (hard constraints).
 */
public class PlanningConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory factory) {
        return new Constraint[] {
                personCannotDoRole(factory),
                personUnavailable(factory),
                personUniquePerDate(factory)
        };
    }

    private Constraint personCannotDoRole(ConstraintFactory factory) {
        // Straf als toegewezen persoon de rol niet mag vervullen.
        return factory.forEach(Dienst.class)
                .filter(dienst -> dienst.getPersoon() != null
                        && !dienst.getPersoon().getRoles().contains(dienst.getRol()))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Person cannot do role");
    }

    private Constraint personUnavailable(ConstraintFactory factory) {
        // Straf als toegewezen persoon niet beschikbaar is op de datum.
        return factory.forEach(Dienst.class)
                .filter(dienst -> dienst.getPersoon() != null
                        && dienst.getPersoon().getUnavailableDates().contains(dienst.getDatum()))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Person unavailable on date");
    }

    // Optioneel: voorkom dat één persoon twee diensten op dezelfde datum krijgt
    private Constraint personUniquePerDate(ConstraintFactory factory) {
        return factory.forEachUniquePair(Dienst.class,
                        Joiners.equal(Dienst::getPersoon),
                        Joiners.equal(Dienst::getDatum))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Person has multiple roles on same date");
    }
}
