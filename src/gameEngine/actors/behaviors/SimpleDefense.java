// This entire file is part of my masterpiece
// Timesh Patel 
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
        actor.killed();
    }

    @Override
    public void handleBullet (BaseProjectile p) {
        myHealth -= p.getInfo().getMyDamage();
        p.died();
    }
}
