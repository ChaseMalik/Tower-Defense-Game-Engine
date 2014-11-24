package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;

public class SlowEffect extends BaseOnHitBehavior {

    public SlowEffect (double duration, double multiplier) {
        super(duration, multiplier);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute (BaseActor actor) {
        // TODO Auto-generated method stub
        if(myDuration==myInitialDuration){
            actor.addDebuff(this);
            BaseMovementBehavior m=((BaseMovementBehavior) actor.getBehavior("movement"));
            m.setSpeed(m.getSpeed()*myMultiplier);
        }
        if(myDuration==0){
            actor.removeDebuff(this);
            BaseMovementBehavior m=((BaseMovementBehavior) actor.getBehavior("movement"));
            m.setSpeed(m.getSpeed()/myMultiplier);
        }
        myDuration--;
    }
    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return null;
    }

}
