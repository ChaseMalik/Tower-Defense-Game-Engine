package gameEngine.actors.behaviors;

import java.util.List;

/**
 * @author $cotty $haw
 *
 */
public abstract class BaseDefendBehavior implements IBehavior{
    
    protected double myHealth;
    protected List<String> myHarmfulBullets;
    //TODO change harmfulBullets to table
    BaseDefendBehavior(double health, List<String> harmfulBullets){
        health=myHealth;
        myHarmfulBullets=harmfulBullets;
    }
}
