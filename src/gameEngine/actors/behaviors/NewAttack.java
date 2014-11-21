package gameEngine.actors.behaviors;

import java.util.Arrays;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseProjectile;

public abstract class NewAttack implements IBehavior {

    protected int myAttackSpeed;
    protected int myCooldown;
    protected final static int READY_TO_SHOOT = 0;
    
    public NewAttack(int attackSpeed){
        myAttackSpeed = attackSpeed;
        myCooldown = READY_TO_SHOOT;
    }

    protected boolean readyToShoot(){
        return myCooldown == READY_TO_SHOOT;
    }
    
    protected void shootActorFromActor(BaseActor target, BaseActor shooter){
        BackendRoute route=new BackendRoute(new Point2D(shooter.getX(), shooter.getY()), new Point2D(target.getX(),(target.getY()))); 
        List<BackendRoute> list = Arrays.asList(route);
        LinearMovement move=new LinearMovement(list, shooter.get.getMySpeed()); 
        projectiles.getChildren().add(new BaseProjectile(myProjectileInfo, move));
        myCooldown=myAttackSpeed;
    }
}
