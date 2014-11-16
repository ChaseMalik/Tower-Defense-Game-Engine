package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;

/**
 * @author $cotty $haw
 *
 */
public abstract class BaseAttackBehavior implements IBehavior {
    protected int myAttSpeed;
    protected double myRange;
    protected BaseActor myBullet;
    public BaseAttackBehavior(int attackSpeed, double range, BaseActor projectile){
        myAttSpeed=attackSpeed;
        myRange=range;
        myBullet=projectile;
    }
}
