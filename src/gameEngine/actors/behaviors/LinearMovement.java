package gameEngine.actors.behaviors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;
import gameEngine.actors.BaseActor;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import utilities.JavaFXutilities.imageView.CenteredImageView;


/**
 * Specific implementation of the movement behavior for an actor, whereby the actor moves
 * from point to point along a route that is randomly chosen from the given options
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class LinearMovement extends BaseMovementBehavior {
    
    private int myIndex = 0;
    
    public LinearMovement(List<Double> list) {
        super(list);
    }

    public LinearMovement(double speed, BackendRoute route){
        super(speed,route);
    }
    @Override
    public void execute (BaseActor actor) {
        if (myIndex == 0) {
            move(actor, myRoute.get(myIndex));
            myIndex++;
            return;
        }
        Point2D current = new Point2D(actor.getX(), actor.getY());
        Point2D destination = myRoute.get(myIndex).getPoint();
        double distance = mySpeed;

        while (distance > destination.distance(current)) {
            myIndex++;
            if (myIndex == myRoute.size()) {
                actor.died();
                return;
            }
            distance -= destination.distance(current);
            current = new Point2D(destination.getX(), destination.getY());
            destination = myRoute.get(myIndex).getPoint();
        }
        myRemainingDistance-=mySpeed;
        Point2D vector = destination.subtract(current).normalize().multiply(mySpeed);
        Point2D answer = current.add(vector);
        move(actor, new VisibilityPoint(myRoute.get(myIndex-1).isVisible(), answer));

    }

    private void move (BaseActor a, VisibilityPoint point) {
        CenteredImageView actor = a.getNode();
        actor.setVisible(point.isVisible());
        actor.setXCenter(point.getPoint().getX());
        actor.setYCenter(point.getPoint().getY());
    }

    @Override
    public IBehavior copy () {
//        return new LinearMovement(mySpeed);
        return null;
    }



}
