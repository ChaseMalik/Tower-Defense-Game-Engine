package gameEngine.actors;

import gameEngine.actors.behaviors.IAct;
import gameEngine.backendExceptions.BackendException;

import java.util.Observable;

/**
 * @author $cotty $haw
 *
 */
public abstract class BaseActor extends Observable implements IAct { //extend Observable or not?
    
    private int myID;
//    private int myHitPoints;
//    private int myAttackPoints;
//    private int myDefendPoints;
//    private static final String ACTOR_SPRITE;
    
    public BaseActor (int ID) throws BackendException {
        myID = ID;
//        myHitPoints = HP;
//        myAttackPoints = AP;
//        myDefendPoints = DP;
    }
    
    /**
     * Updates the actor's position
     */
    public abstract void update ();
    
    /**
     * Executes the actor's on-death actions
     */
    public abstract void onDeath ();

}
