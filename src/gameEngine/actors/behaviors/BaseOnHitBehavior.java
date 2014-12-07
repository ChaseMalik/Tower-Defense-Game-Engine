package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import java.util.Set;

public abstract class BaseOnHitBehavior implements IBehavior{
    double myDuration;
    double myInitialDuration;
    double myMultiplier;
    protected String myString;
    public BaseOnHitBehavior(double duration, double multiplier){
        myDuration=duration;
        myInitialDuration=duration;
        myMultiplier=multiplier;
    }
    public BaseOnHitBehavior(double multiplier){
        this(30.0, multiplier);
    }

    public abstract void undo(BaseActor actor);    
    
    @Override
    public Set<Class<? extends BaseActor>> getType () {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public String toString(){
        return myString;
    }

}
