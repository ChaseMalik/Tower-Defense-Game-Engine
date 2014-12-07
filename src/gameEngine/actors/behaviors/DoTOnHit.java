package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;


public class DoTOnHit extends BaseOnHitBehavior {

    public DoTOnHit (double duration, double damage) {
        super(duration, damage);
        // TODO Auto-generated constructor stub
    }
    public DoTOnHit (double damage){
        this(30.0, damage);
    }
    @Override
    public void execute (BaseActor actor) {
        // TODO Auto-generated method stub
        BaseDefendBehavior m = ((BaseDefendBehavior) actor.getBehavior("defend"));
        double d = m.getHealth()-myMultiplier/myInitialDuration;
        m.setHealth(d);
 
        if(m.getHealth()<=0){
            
            actor.killed();
        }
        if (myDuration == 0) {
            actor.removeDebuff(this);
            undo(actor);
        }
        myDuration--;
    }

    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return new DoTOnHit(myDuration, myMultiplier);
    }

    @Override
    public void undo (BaseActor actor) {
        // TODO Auto-generated method stub
      
    }

}
