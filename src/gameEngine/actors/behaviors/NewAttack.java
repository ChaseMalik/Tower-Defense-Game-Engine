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
import gameEngine.actors.RealActor;

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
    
    protected void shootActorFromActor(BaseActor target, BaseActor actor){
        RealActor shooter=(RealActor) actor;
        BackendRoute route=new BackendRoute(new Point2D(shooter.getX(), shooter.getY()), new Point2D(target.getX(),(target.getY()))); 
        List<BackendRoute> list = Arrays.asList(route);
        LinearMovement move=new LinearMovement(list, shooter.getProjectile().getMySpeed()); 
        BaseProjectile projectile=new BaseProjectile(move); 
        List<BaseActor> pList=new ArrayList<>();
        pList.add(projectile);
        shooter.spawnProjectile(pList);
        myCooldown=myAttackSpeed;
    }

    public void setAttackSpeed (int i) {
        // TODO Auto-generated method stub
        myAttackSpeed=i;
        
    }
    @Override
    public Set<Class<? extends BaseActor>> getType () {
        // TODO Auto-generated method stub
        Set a= new HashSet<Class<? extends BaseActor>>();
        
        return a;
    }
}
