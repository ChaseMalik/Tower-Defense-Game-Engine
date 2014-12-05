package gameEngine.actors.behaviors;

import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseProjectile;
import gameEngine.actors.RealActor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.geometry.Point2D;


public abstract class RangeAttack extends BaseAttack {

    private static final int IMAGE_ANGLE_OFFSET = 270;
    private static final double DISTANCE_FACTOR = 2.0;

    public RangeAttack (double attackSpeed) {
        super(attackSpeed);
    }

    protected void shootActorFromActor (BaseActor target, BaseActor actor) {
        RealActor shooter = (RealActor) actor;
        Point2D shooterLoc = new Point2D(shooter.getX(), shooter.getY());
        Point2D targetLoc = new Point2D(target.getX(), target.getY());
        Point2D unitVector = targetLoc.subtract(shooterLoc).normalize();
        double heading = getOrientation(unitVector);
        BackendRoute route = new BackendRoute(shooterLoc, shooterLoc.add(unitVector.multiply(BuildingPane.DRAW_SCREEN_WIDTH * DISTANCE_FACTOR)));
        BaseProjectile projectile = new BaseProjectile(shooter.getProjectile().copy());
        projectile.getInfo().getMove().setRoute(route);
        shooter.spawnProjectile(projectile);
        shooter.getNode().setRotate(heading);
        projectile.getNode().setRotate(heading);
        myCooldown = (int) myAttackSpeed;
    }

    private double getOrientation (Point2D dif) {
        return Math.toDegrees(Math.atan2(dif.getY(), dif.getX())) + IMAGE_ANGLE_OFFSET;
    }
}
