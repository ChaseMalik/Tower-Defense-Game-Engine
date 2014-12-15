// This entire file is part of my masterpiece.
// Chase Malik
package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import java.util.Comparator;
import java.util.List;
import javafx.geometry.Point2D;


/**
 * Example of BaseAttack, where the actor shoots at the nearest opposing actor
 * 
 * @author Chase Malik
 *
 */
public class ClosestRangeAttack extends RangedTowerAttack {
   
    public ClosestRangeAttack(List<Double> list){
        super(list);
    }

    /**
     * Returns a comparator such that the actor closest to the provided actor is returned first
     * 
     * @param actor the actor for which other actor's distances are being compared
     */
    @Override
    public Comparator<BaseActor> defineTargetComparison (BaseActor actor) {
        Point2D current = new Point2D(actor.getX(), actor.getY());
        return (BaseActor a1, BaseActor a2) -> Double
                .compare(current.distance(a1.getX(), a1.getY()),
                         current.distance(a2.getX(), a2.getY()));
    }

    @Override
    public IBehavior copy () {
        return new ClosestRangeAttack(myList);
    }

}
