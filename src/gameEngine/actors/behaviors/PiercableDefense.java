// This entire file is part of my masterpiece
// Timesh Patel 
package gameEngine.actors.behaviors;

import java.util.List;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseProjectile;

public class PiercableDefense extends BaseDefendBehavior{

    public PiercableDefense(List<Double> list){
        super(list);
    }
    PiercableDefense (double health) {
        super(health);
    }

    @Override
    public IBehavior copy () {
        return new PiercableDefense(myList);
    }

    @Override
    public void handleBullet (BaseProjectile p) {
        double d=p.getInfo().getMyDamage();
        if(d-myHealth>0){
            p.getInfo().setMyDamage(d-myHealth);
        }else{
            p.died();
        }
        myHealth-=d;
    }

    @Override
    public void onDeath (BaseActor actor) {
        actor.killed();
    }

}
