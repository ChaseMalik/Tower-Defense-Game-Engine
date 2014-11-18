package gameEngine.actors.behaviors;

import java.util.List;


/**
 *  Behavior that defines the defense of an actor
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public abstract class BaseDefendBehavior implements IBehavior {

    protected double myHealth;
    protected List<String> myHarmfulBullets;

    // TODO change harmfulBullets to table
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
}
