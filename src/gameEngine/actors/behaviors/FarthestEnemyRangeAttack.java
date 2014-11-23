package gameEngine.actors.behaviors;

import java.util.List;
import javafx.geometry.Point2D;
import gameEngine.actors.BaseActor;
import gameEngine.actors.RealActor;

public class FarthestEnemyRangeAttack extends RangeAttack{

    public FarthestEnemyRangeAttack (double attackSpeed) {
        super(attackSpeed);
    }

    @Override
    public void performAttack (BaseActor actor) {
        RealActor shooter=(RealActor)actor;
        BaseActor shootable=getFarthestActor(shooter, actor.getEnemiesInRange());
        if(shootable.equals(null))
            return;
        shootActorFromActor(shootable, actor);
    }
    
    private BaseActor getFarthestActor(BaseActor actor, List<BaseActor> enemies) {   
        BaseActor close = null;
        double distance = Integer.MAX_VALUE;
        for(BaseActor e: enemies){
           BaseMovementBehavior m= (BaseMovementBehavior)e.getBehavior("movement");
           if(m.getRemainingDistance()<distance){
               close=e;
               distance=m.getRemainingDistance();
           }
        }
        return close;
    }
    @Override
    public IBehavior copy () {
        return new FarthestEnemyRangeAttack(myAttackSpeed);
    }
    

}
