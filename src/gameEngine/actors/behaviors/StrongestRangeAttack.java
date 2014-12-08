package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import java.util.Comparator;
import java.util.List;


public class StrongestRangeAttack extends RangeAttack {

    public static final String DEFEND = "defend";
    public StrongestRangeAttack(List<Double> list){
        super(list);
    }
    public StrongestRangeAttack (double attackSpeed) {
        super(attackSpeed);
    }

    @Override
    public Comparator<BaseActor> defineComparison (BaseActor a) {
        return (BaseActor a1, BaseActor a2) -> Double
                .compare(((BaseDefendBehavior) (a2.getBehavior(DEFEND))).getHealth(),
                         ((BaseDefendBehavior) (a1.getBehavior(DEFEND))).getHealth());
    }

    @Override
    public IBehavior copy () {
        return new StrongestRangeAttack(myList);
    }


}
