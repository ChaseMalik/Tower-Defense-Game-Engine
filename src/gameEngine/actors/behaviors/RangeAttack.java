package gameEngine.actors.behaviors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseProjectile;
import gameEngine.actors.RealActor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.geometry.Point2D;

public abstract class RangeAttack extends BaseAttack{

    public RangeAttack (int attackSpeed) {
        super(attackSpeed);
        // TODO Auto-generated constructor stub
    }
    
    protected void shootActorFromActor(BaseActor target, BaseActor actor){
        RealActor shooter=(RealActor) actor;
        BackendRoute route=new BackendRoute(new Point2D(shooter.getX(), shooter.getY()), new Point2D(target.getX(),(target.getY()))); 
        List<BackendRoute> list = Arrays.asList(route);
        LinearMovement move=new LinearMovement(list, shooter.getProjectile().getSpeed()); 
        BaseProjectile projectile=new BaseProjectile(move); 
        List<BaseActor> pList=new ArrayList<>();
        pList.add(projectile);
        shooter.spawnProjectile(pList);
        myCooldown=(int)myAttackSpeed;
    }
}
