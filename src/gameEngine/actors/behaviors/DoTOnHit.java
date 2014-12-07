package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;


public class DoTOnHit extends BaseOnHitBehavior {

    public DoTOnHit (double duration, double multiplier) {
        super(duration, multiplier);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute (BaseActor actor) {
        // TODO Auto-generated method stub
        BaseDefendBehavior m = ((BaseDefendBehavior) actor.getBehavior("defend"));
        double d = m.getHealth()-myMultiplier;
        m.setHealth(d);
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
        BaseMovementBehavior m = ((BaseMovementBehavior) actor.getBehavior("movement"));
        m.setSpeed(m.getSpeed() / myMultiplier);
    }

}
