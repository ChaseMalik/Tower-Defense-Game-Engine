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
        // TODO Auto-generated constructor stub
    }

    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return new PiercableDefense(myHealth);
    }

    @Override
    public void handleBullet (BaseProjectile p) {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        actor.killed();
    }

}
