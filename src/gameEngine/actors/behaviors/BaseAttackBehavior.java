package gameEngine.actors.behaviors;

import gameEngine.actors.ProjectileInfo;

/**
 * @author $cotty $haw
 *
 */
public abstract class BaseAttackBehavior implements IBehavior {
    protected int myAttSpeed;
    protected double myRange;
    protected ProjectileInfo myBullet;
    public BaseAttackBehavior(int attackSpeed, double range, ProjectileInfo projectile){
        myAttSpeed=attackSpeed;
        myRange=range;
        myBullet=projectile;
    }
}
