package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;

public class SlowEffect extends BaseOnHitBehavior {
   
    public SlowEffect (double duration, double multiplier) {
        super(duration, multiplier);
        // TODO Auto-generated constructor stub
        myString="slowEffect";
    }
    public SlowEffect(double multiplier){
        this(30.0,multiplier);
    }
    @Override
    public void execute (BaseActor actor) {
        // TODO Auto-generated method stub
        if(myDuration==myInitialDuration){
            BaseMovementBehavior m=((BaseMovementBehavior) actor.getBehavior("movement"));
            double d=m.getSpeed()*myMultiplier;
            m.setSpeed(d);
        }
        if(myDuration==0){
            actor.removeDebuff(this);
            undo(actor);
        }
        myDuration--;
    }
    
    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return new SlowEffect(myDuration,myMultiplier);
    }

    @Override
    public void undo (BaseActor actor) {
        // TODO Auto-generated method stub
        BaseMovementBehavior m=((BaseMovementBehavior) actor.getBehavior("movement"));
        m.setSpeed(m.getSpeed()/myMultiplier);
    }

}
