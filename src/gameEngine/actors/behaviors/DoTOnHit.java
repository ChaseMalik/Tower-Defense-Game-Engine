package gameEngine.actors.behaviors;

import java.util.List;
import gameEngine.actors.BaseActor;


public class DoTOnHit extends BaseOnHitBehavior {
    
    public DoTOnHit(List<Double> list){
        super(list);
    }
    public DoTOnHit (double duration, double damage) {
        super(duration, damage);
        // TODO Auto-generated constructor stub
    }
    public DoTOnHit (double damage){
        this(30.0, damage);
    }

    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return new DoTOnHit(myList);
    }
    @Override
    public void during (BaseActor actor) {
        // TODO Auto-generated method stub
        BaseDefendBehavior m = ((BaseDefendBehavior) actor.getBehavior("defend"));
        double d = m.getHealth()-myMultiplier/myInitialDuration;
        m.setHealth(d);
 
        if(m.getHealth()<=0){
            
            actor.killed();
        }
    }
    @Override
    public void start (BaseActor actor) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void end (BaseActor actor) {
        // TODO Auto-generated method stub
        actor.removeDebuff(this);
    }



}
