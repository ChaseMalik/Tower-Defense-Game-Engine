package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import java.util.Set;

public abstract class BaseEffectBehavior implements IBehavior {

    protected double myChange;
    
    public BaseEffectBehavior(double change){
        myChange = change;
    }
    
    public abstract void performEffect(BaseActor actor);
}
