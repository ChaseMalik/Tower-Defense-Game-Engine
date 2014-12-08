package gameEngine.actors.behaviors;

import java.util.List;
import gameEngine.actors.BaseActor;

public class SlowEffect extends BaseOnHitBehavior {
    private double myMultiplier;
    public SlowEffect (double duration, double multiplier) {
        super(duration, multiplier);
        // TODO Auto-generated constructor stub
        myString="slowEffect";
    }
    public SlowEffect(double multiplier){
        this(30.0,multiplier);
    }
    public SlowEffect (List<Double> list){
        super(list);
        myMultiplier=list.get(1)/100.0;
        
    }
    
    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return new SlowEffect(myList);
    }

    @Override
    public void end (BaseActor actor) {
        // TODO Auto-generated method stub
        BaseMovementBehavior m=((BaseMovementBehavior) actor.getBehavior("movement"));
        m.setSpeed(m.getSpeed()/myMultiplier);
    }
    @Override
    public void during (BaseActor actor) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void start (BaseActor actor) {
        // TODO Auto-generated method stub
        BaseMovementBehavior m=((BaseMovementBehavior) actor.getBehavior("movement"));
        double d=m.getSpeed()*myMultiplier;
        m.setSpeed(d);
    }


}
