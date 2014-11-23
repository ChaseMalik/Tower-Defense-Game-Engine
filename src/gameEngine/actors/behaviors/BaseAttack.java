package gameEngine.actors.behaviors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseProjectile;
import gameEngine.actors.BaseTower;
import gameEngine.actors.RealActor;
/**
 * Represents the basic attack behavior, where projectile(s) to be fired
 * @author Chase Malik, Timesh Patel
 *
 */
public abstract class BaseAttack implements IBehavior {

    protected double myAttackSpeed;
    protected int myCooldown;
    protected final static int READY_TO_SHOOT = 0;
    
    public BaseAttack(double attackSpeed){
        myAttackSpeed = attackSpeed;
        myCooldown = READY_TO_SHOOT;
    }

    protected boolean readyToShoot(){
        return myCooldown == READY_TO_SHOOT;
    }
    

    public void setAttackSpeed (int i) {
        // TODO Auto-generated method stub
        myAttackSpeed=i;
        
    }
    @Override
    public Set<Class<? extends BaseActor>> getType () {
        // TODO Auto-generated method stub
        Set<Class<? extends BaseActor>> a= new HashSet<Class<? extends BaseActor>>();
        a.add(BaseTower.class);
        a.add(BaseEnemy.class);
        return a;
    }
}
