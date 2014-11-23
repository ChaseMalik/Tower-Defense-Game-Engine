package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
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
    protected List<String> myHarmfulBullets;

    
    BaseDefendBehavior (double health, List<String> harmfulBullets) {
        health = myHealth;
        myHarmfulBullets = harmfulBullets;
    }
    public double getHealth(){
        return myHealth;
    }
    
    public List<String> getHarmfulBullets(){
        return myHarmfulBullets;
    }
    @Override
    public Set<Class<? extends BaseActor>> getType () {
        // TODO Auto-generated method stub
        return null;
    }
}
