package gameEngine.actors.behaviors;

import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;
import gameEngine.actors.BaseActor;


/**
 * Specific implementation of the movement behavior for an actor, whereby the actor moves
 * from point to point along a route that is randomly chosen from the given options
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class LinearMovement extends BaseMovementBehavior {
    private int myIndex = 0;

    public LinearMovement (List<BackendRoute> routeOptions, double speed) {
        super(routeOptions, speed);
    }

    @Override
    public void execute (BaseActor actor) {
        if (myIndex == 0) {
            move(actor, myRoute.get(myIndex));
            myIndex++;
            return;
        }
        if (myIndex == myRoute.size()) {
            // TODO handle this
            return;
        }
        Point2D current = new Point2D(actor.getX(), actor.getY());
        Point2D destination = myRoute.get(myIndex).getPoint();
        double distance = mySpeed;

        while (distance > destination.distance(current)) {
            myIndex++;
            if (myIndex == myRoute.size()) {
                // TODO Handle this
                return;
            }
            distance -= destination.distance(current);
            current = new Point2D(destination.getX(), destination.getY());
            destination = myRoute.get(myIndex).getPoint();
        }

        Point2D vector = destination.subtract(current).normalize().multiply(mySpeed);
        Point2D answer = current.add(vector);
        move(actor, new VisibilityPoint(myRoute.get(myIndex).isVisible(), answer));

    }

    private void move (BaseActor a, VisibilityPoint point) {
        ImageView actor = a.getNode();
        actor.setVisible(point.isVisible());
        actor.setX(point.getPoint().getX());
        actor.setY(point.getPoint().getY());
    }

    @Override
    public IBehavior copy () {
        return new LinearMovement(myOptions, mySpeed);
    }

}
