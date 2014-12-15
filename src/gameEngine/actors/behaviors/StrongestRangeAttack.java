package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import java.util.Comparator;
import java.util.List;


public class StrongestRangeAttack extends RangedTowerAttack {

    public static final String DEFEND_NAME = "defend";
    public StrongestRangeAttack(List<Double> list){
        super(list);
    }

    @Override
    public Comparator<BaseActor> defineTargetComparison (BaseActor a) {
        return (BaseActor a1, BaseActor a2) -> Double
                .compare(((BaseDefendBehavior) (a2.getBehavior(DEFEND_NAME))).getHealth(),
                         ((BaseDefendBehavior) (a1.getBehavior(DEFEND_NAME))).getHealth());
    }

    @Override
    public IBehavior copy () {
        return new StrongestRangeAttack(myList);
    }


}
