package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import java.util.Comparator;
import java.util.List;
import javafx.geometry.Point2D;


/**
 * Example of BaseAttack, where the actor shoots at the nearest opposing actor
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class ClosestRangeAttack extends RangeAttack {
   
    public ClosestRangeAttack(List<Double> list){
        super(list);
    }
    public ClosestRangeAttack (double attackSpeed) {
        super(attackSpeed);
    }

    @Override
    public Comparator<BaseActor> defineComparison (BaseActor actor) {
        Point2D current = new Point2D(actor.getX(), actor.getY());
        return (BaseActor a1, BaseActor a2) -> Double
                .compare(current.distance(a1.getX(), a1.getY()),
                         current.distance(a2.getX(), a2.getY()));
    }

    @Override
    public IBehavior copy () {
        return new ClosestRangeAttack(myAttackSpeed);
    }

}
