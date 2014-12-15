// This entire file is part of my masterpiece.
// Chase Malik
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


/**
 * 
 * Attack behaviors for towers based on the attack range of the tower
 * 
 * @author Chase Malik
 *
 */
public abstract class RangedTowerAttack extends BaseAttack {

    private static final int IMAGE_ANGLE_OFFSET = 270;
    private static final double DISTANCE_FACTOR = 2.0;

    public RangedTowerAttack (List<Double> list) {
        super(list);
    }
    /**
     * Method that generates a projectile that is headed towards the target from 
     * the shooter and that can travel to the end of the map if it misses the target
     * or pierces the target
     * 
     * @param target the BaseActor being shot at
     * @param actor the BaseActor shooting some one else
     */
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

    /**
     * Implementation of the abstract method from BaseAttack
     * On perform attack, the behavior finds the actor to shoot at based on a comparator defined
     * in concrete classes.  Then if there is a valid target the actor shoots at it
     */
    @Override
    protected void performAttack (BaseActor actor) {
        Optional<BaseActor> target =
                actor.getEnemiesInRange(myRange).stream().min(defineTargetComparison(actor));
        if (target.isPresent()) {
            shootActorFromActor(target.get(), actor);
        }
    }

    /**
     * Defines the method of comparison between potential targets
     * Note: Requires the minimum result to be the desired target
     * 
     * @param actor used as reference for comparison
     * @return Comparator used to locate specific target 
     */
    protected abstract Comparator<BaseActor> defineTargetComparison (BaseActor actor);

    /**
     * Method that converts the orientation of the shooter as a vector into degrees
     * This also adds the image offset which is based on the fact that you are expected to provide images 
     * facing down in the authoring environment
     * 
     * @param unit - unit vector representing the shooters orientation
     * @return degrees associated with the unit vector offset by the image offset
     */
    private double getOrientation (Point2D unit) {
        return Math.toDegrees(Math.atan2(unit.getY(), unit.getX())) + IMAGE_ANGLE_OFFSET;
    }
}
