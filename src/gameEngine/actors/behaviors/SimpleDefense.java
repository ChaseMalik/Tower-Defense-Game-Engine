package gameEngine.actors.behaviors;

import java.util.List;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseProjectile;


public class SimpleDefense extends BaseDefendBehavior {
    public SimpleDefense (double health) {
        super(health);
    }
    public SimpleDefense(List<Double> list){
        super(list);
    }
    @Override
    public IBehavior copy () {
        return new SimpleDefense(myList);
    }

    @Override
    public void onDeath (BaseActor actor) {
        // TODO Auto-generated method stub
        actor.killed();
    }

    @Override
    public void handleBullet (BaseProjectile p) {
        // TODO Auto-generated method stub
        myHealth -= p.getInfo().getMyDamage();
        p.died();
    }
}
