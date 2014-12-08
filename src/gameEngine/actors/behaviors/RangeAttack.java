package gameEngine.actors.behaviors;

import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseProjectile;
import gameEngine.actors.RealActor;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Point2D;


public abstract class RangeAttack extends BaseAttack {

    private static final int IMAGE_ANGLE_OFFSET = 270;
    private static final double DISTANCE_FACTOR = 2.0;

    public RangeAttack (double attackSpeed) {
        super(attackSpeed);
    }

    public RangeAttack (List<Double> list) {
        // TODO Auto-generated constructor stub
        super(list);
    }

    protected void shootActorFromActor (BaseActor target, BaseActor actor) {
        RealActor shooter = (RealActor) actor;
        Point2D shooterLoc = new Point2D(shooter.getX(), shooter.getY());
        Point2D targetLoc = new Point2D(target.getX(), target.getY());
        Point2D unitVector = targetLoc.subtract(shooterLoc).normalize();
        double heading = getOrientation(unitVector);
        BackendRoute route =
                new BackendRoute(shooterLoc, shooterLoc.add(unitVector
                        .multiply(BuildingPane.DRAW_SCREEN_WIDTH * DISTANCE_FACTOR)));
        BaseProjectile projectile = new BaseProjectile(shooter.getProjectile().copy());
        projectile.getInfo().getMove().setRoute(route);
        shooter.spawnProjectile(projectile);
        shooter.getNode().setRotate(heading);
        projectile.getNode().setRotate(heading);
    }

    @Override
    protected void performAttack (BaseActor actor) {
//        System.out.println(actor.getRangeProperty());
        Optional<BaseActor> target =
                actor.getEnemiesInRange(myRange).stream().min(defineComparison(actor));
        if (target.isPresent()) {
            shootActorFromActor(target.get(), actor);
        }
    }

    protected abstract Comparator<BaseActor> defineComparison (BaseActor actor);

    private double getOrientation (Point2D dif) {
        return Math.toDegrees(Math.atan2(dif.getY(), dif.getX())) + IMAGE_ANGLE_OFFSET;
    }
}
