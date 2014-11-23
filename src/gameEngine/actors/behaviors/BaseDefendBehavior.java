package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseProjectile;
import gameEngine.actors.BaseTower;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 *  Behavior that defines the defense of an actor
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public abstract class BaseDefendBehavior implements IBehavior {

    protected double myHealth;

    
    BaseDefendBehavior (double health) {
        health = myHealth;
    }
    public double getHealth(){
        return myHealth;
    }
    

    @Override
    public Set<Class<? extends BaseActor>> getType () {
        // TODO Auto-generated method stub
        Set<Class<? extends BaseActor>> a= new HashSet<Class<? extends BaseActor>>();
        a.add(BaseProjectile.class);
        return a;
    }
}
