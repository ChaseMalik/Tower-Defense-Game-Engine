package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import java.util.Set;

public abstract class BaseOnHitBehavior implements IBehavior{
    double myDuration;
    double myInitialDuration;
    double myMultiplier;
    public BaseOnHitBehavior(double duration, double multiplier){
        myDuration=duration;
        myInitialDuration=duration;
        myMultiplier=multiplier;
    }



    @Override
    public Set<Class<? extends BaseActor>> getType () {
        // TODO Auto-generated method stub
        return null;
    }

}
