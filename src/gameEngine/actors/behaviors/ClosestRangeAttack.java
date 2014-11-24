package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;
import java.util.List;
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
        List<BaseActor> shootable = getShootableActors(actor);
        if(shootable==null)
            return;
        BaseActor enemy = getClosestActor(actor, shootable);
        shootActorFromActor(enemy, actor);
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

    private BaseActor getClosestActor (BaseActor actor, List<BaseActor> enemies) {
        Point2D current = new Point2D(actor.getX(), actor.getY());
        BaseActor close = null;
        double distance = Integer.MAX_VALUE;
        for(BaseActor e: enemies){
            Point2D point = new Point2D(e.getX(), e.getY());
            if(distance < current.distance(point)){
                close = e;
                distance = current.distance(point);
            }
        }
        return close;
    }

    @Override
    public IBehavior copy () {
        return new ClosestRangeAttack(myAttackSpeed);
    }

}
