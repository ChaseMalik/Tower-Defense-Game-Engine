// This entire file is part of my masterpiece.
// Chase Malik
package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import java.util.List;


/**
 * Represents the basic attack behavior, where projectile(s) are fired
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public abstract class BaseAttack implements IBehavior {
    protected List<Double> myList;
    protected double myAttackSpeed;
    protected double myRange;
    protected int myCooldown;
    protected final static int READY_TO_SHOOT = 0;
    private final static String ATTACK_NAME = "attack";
    private static final int ATTACK_SPEED_LOCATION = 0;
    private static final int RANGE_LOCATION = 1;

    public BaseAttack(List<Double> list){
        myList=list;
        myAttackSpeed=list.get(ATTACK_SPEED_LOCATION);
        myRange=list.get(RANGE_LOCATION);
        myCooldown = READY_TO_SHOOT;
    }
    
    /**
     * Checks to see if it is ready to shoot
     * @return true if its cooldown timer has expired and false otherwise
     */
    protected boolean readyToShoot () {
        return myCooldown <= READY_TO_SHOOT;
    }

    /**
     * Implementation of behavior on every update within the game
     * Checks to see if it is ready to shoot and if it is it performs its attack and resets its cooldown
     * otherwise it decreases its cooldown timer
     */
    @Override
    public void execute (BaseActor actor) {
        if (readyToShoot()) {
            performAttack(actor);
            myCooldown = (int) myAttackSpeed;
        }
        else {
            myCooldown--;
        }

    }
    /**
     * Attack to be performed on execute when ready to shoot
     */
    protected abstract void performAttack (BaseActor actor);
    
    /**
     * Return name of behavior
     */
    public String toString () {
        return ATTACK_NAME;
    }
    
    /**
     * Method that returns the attack range
     * 
     * @return attack range
     */
    public double getRange(){
        return myRange;
    }
}
