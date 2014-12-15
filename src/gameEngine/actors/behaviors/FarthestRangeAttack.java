package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import java.util.Comparator;
import java.util.List;


public class FarthestRangeAttack extends RangedTowerAttack {

    private static final String MOVEMENT_NAME = "movement";
    public FarthestRangeAttack (List<Double> list) {
        super(list);
    }

    @Override
    public Comparator<BaseActor> defineTargetComparison (BaseActor a) {
        return (BaseActor a1, BaseActor a2) -> Double
                .compare(((BaseMovementBehavior) (a1.getBehavior(MOVEMENT_NAME)))
                        .getRemainingDistance(),
                         ((BaseMovementBehavior) (a2.getBehavior(MOVEMENT_NAME)))
                                 .getRemainingDistance());
    }

    @Override
    public IBehavior copy () {
        return new FarthestRangeAttack(myList);
    }

}
