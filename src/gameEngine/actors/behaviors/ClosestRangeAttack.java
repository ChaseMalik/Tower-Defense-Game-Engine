package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.omg.CORBA.Current;
import javafx.geometry.Point2D;
/**
 * Example of BaseAttack, where the actor shoots at the nearest opposing actor
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class ClosestRangeAttack extends RangeAttack {

    public ClosestRangeAttack (double attackSpeed) {
        super(attackSpeed);
    }

    @Override
    public void performAttack (BaseActor actor) {
        Point2D current = new Point2D(actor.getX(), actor.getY());
        Comparator<BaseActor> byClosest = (BaseActor a1, BaseActor a2) -> Double
                .compare(current.distance(a1.getX(),a1.getY()), current.distance(a2.getX(),a2.getY()));
        Optional<BaseActor>shootable = getShootableActors(actor).stream().sorted(byClosest).findFirst();
        if(shootable.isPresent()){
            shootActorFromActor(shootable.get(),actor);
        }
    }

    private List<BaseActor> getShootableActors (BaseActor actor) {
        if(actor instanceof BaseEnemy){
            return actor.getTowersInRange();
        }
        else if(actor instanceof BaseTower){
            return actor.getEnemiesInRange();
        }
        return null;
        
    }

    @Override
    public IBehavior copy () {
        return new ClosestRangeAttack(myAttackSpeed);
    }

}
