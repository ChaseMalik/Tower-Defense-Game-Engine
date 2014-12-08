package gameEngine.actors.behaviors;

import java.util.HashSet;
import java.util.Set;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;


/**
 * Represents the basic attack behavior, where projectile(s) to be fired
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public abstract class BaseAttack implements IBehavior {

    protected double myAttackSpeed;
    protected int myCooldown;
    protected final static int READY_TO_SHOOT = 0;
    private final static String myName = "attack";

    public BaseAttack (double attackSpeed) {
        myAttackSpeed = attackSpeed;
        myCooldown = READY_TO_SHOOT;
    }

    protected boolean readyToShoot () {
        return myCooldown == READY_TO_SHOOT;
    }

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

    protected abstract void performAttack (BaseActor actor);

    public void setAttackSpeed (int i) {
        myAttackSpeed = i;

    }



    public String toString () {
        return myName;
    }
}
