package gameEngine.actors.behaviors;

import java.util.List;
import javafx.geometry.Point2D;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;

public class CloseAttack extends NewAttack {

    public CloseAttack (int attackSpeed) {
        super(attackSpeed);
    }

    @Override
    public void execute (BaseActor actor) {
        List<BaseActor> shootableActors = getShootableActors(actor);
        List<BaseEnemy> enemies = actor.getEnemiesInRange();
        if(!readyToShoot()){
            myCooldown--;
            return;
        }
        BaseEnemy enemy = getClosestEnemy(actor, enemies);
        shootAtEnemyFromActor(enemy, actor);
    }

    private List<BaseActor> getShootableActors (BaseActor actor) {
        if(actor instanceof BaseEnemy){
            return actor.getEnemiesInRange();
        }
        else if(actor instanceof BaseTower){
            return actor.getTowersInRange();
        }
        
    }

    private BaseEnemy getClosestActor (BaseActor actor, List<BaseEnemy> enemies) {
        Point2D current = new Point2D(actor.getX(), actor.getY());
        BaseEnemy close = null;
        double distance = Integer.MAX_VALUE;
        for(BaseEnemy e: enemies){
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

}
