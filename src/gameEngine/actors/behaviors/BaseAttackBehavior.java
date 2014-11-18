package gameEngine.actors.behaviors;

import gameEngine.actors.ProjectileInfo;

/**
 * @author $cotty $haw
 *
 */
public abstract class BaseAttackBehavior implements IBehavior {
    protected int myAttackSpeed;
    protected double myRange;
    protected int myCooldown;
    protected ProjectileInfo myProjectileInfo;
    
    public BaseAttackBehavior(int attackSpeed, double range, ProjectileInfo projectile){
        myAttackSpeed=attackSpeed;
        myRange=range;
        myProjectileInfo=projectile;
    }
    
    public int getAttackSpeed(){
        return myAttackSpeed;
    }
    
    public double getRange(){
        return myRange;
    }
    
    public ProjectileInfo getProjectileInfo(){
        return myProjectileInfo;
    }
}
