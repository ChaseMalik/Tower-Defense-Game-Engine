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

    public RangeAttack (double attackSpeed) {
        super(attackSpeed);
    }
    
    protected void shootActorFromActor(BaseActor target, BaseActor actor){
        RealActor shooter=(RealActor) actor;
        BackendRoute route=new BackendRoute(new Point2D(shooter.getX(), shooter.getY()), new Point2D(target.getX(),(target.getY()))); 
        BaseProjectile projectile=new BaseProjectile(shooter.getProjectile().copy());
        projectile.getInfo().getMove().setRoute(route);
        shooter.spawnProjectile(projectile);
        myCooldown=(int)myAttackSpeed;
    }
}
