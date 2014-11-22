package gameEngine.actors.behaviors;

import java.util.List;
import java.util.Set;
import javafx.geometry.Point2D;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;
/**
 * Example of BaseAttack, where the actor shoots at the nearest opposing actor
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class ClosestAttack extends BaseAttack {

    public ClosestAttack (int attackSpeed) {
        super(attackSpeed);
    }

    @Override
    public void execute (BaseActor actor) {
        if(!readyToShoot()){
            myCooldown--;
            return;
        }
        List<BaseActor> shootable = getShootableActors(actor);
        if(shootable.equals(null))
            return;
        BaseActor enemy = getClosestActor(actor, shootable);
        shootActorFromActor(enemy, actor);
    }

    private List<BaseActor> getShootableActors (BaseActor actor) {
        if(actor instanceof BaseEnemy){
            return actor.getEnemiesInRange();
        }
        else if(actor instanceof BaseTower){
            return actor.getTowersInRange();
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Class<? extends BaseActor>> getType () {
        // TODO Auto-generated method stub
        return null;
    }

}
