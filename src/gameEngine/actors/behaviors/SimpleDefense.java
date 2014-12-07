package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseProjectile;

public class SimpleDefense extends BaseDefendBehavior{
    public SimpleDefense(double health){
        super(health);
    }

    @Override
    public void execute (BaseActor actor) {
        for(BaseActor a: actor.getInfoObject().getProjectilesInRange()){
            if(actor.getNode().intersects(a.getRange().getBoundsInLocal())){
            if(((BaseProjectile) a).getInfo().getOnHit()!=null){
                for(IBehavior e: ((BaseProjectile)a).getInfo().getOnHit()){
                    actor.addDebuff(e.copy());
                }
            }
            myHealth--;
            a.died(1);
            if(myHealth<=0){
                //TODO add enemy cost
                actor.died(1);
            }
        }
        }
    }

    @Override
    public IBehavior copy () {
        return new SimpleDefense(myInitialHealth);
    }
}
