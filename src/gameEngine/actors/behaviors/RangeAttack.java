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
        Point2D shooterLoc = new Point2D(shooter.getX(), shooter.getY());
        Point2D targetLoc = new Point2D(target.getX(),target.getY());
        BackendRoute route=new BackendRoute(shooterLoc, targetLoc); 
        BaseProjectile projectile=new BaseProjectile(shooter.getProjectile().copy());
        projectile.getInfo().getMove().setRoute(route);
        shooter.spawnProjectile(projectile);
        double heading = getOrientation(shooterLoc,targetLoc);
        shooter.getNode().setRotate(-heading);
        projectile.getNode().setRotate(-heading);
        myCooldown=(int)myAttackSpeed;
    }

    private double getOrientation (Point2D shooter, Point2D target) {
        Point2D dif = shooter.subtract(target).normalize();
        return Math.toDegrees(Math.atan2(dif.getX(), dif.getY())) + 180;
    }
}
