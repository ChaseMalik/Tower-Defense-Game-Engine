package gameEngine.actors.behaviors;

import java.util.List;
import java.util.Random;
import java.util.Set;
import javafx.geometry.Point2D;
import utilities.JavaFXutilities.imageView.CenteredImageView;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;
import gameEngine.actors.BaseActor;


/**
 *  Behavior that defines the movement of an actor
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public abstract class BaseMovementBehavior implements IBehavior {

    protected String myString;
    protected List<VisibilityPoint> myRoute;
    protected double mySpeed;
    protected double myRemainingDistance;

    public BaseMovementBehavior (double speed) {
        mySpeed = speed;
        myString="movement";
    }
    public BaseMovementBehavior (double speed, BackendRoute route) {
        mySpeed = speed;
        myRoute=route.getPoints();
    }
    private double calculateTotalDistance (List<VisibilityPoint> route) {
        double distance = 0;
        for(int i = 0; i<route.size()-1;i++){
            distance += route.get(i).getPoint().distance(route.get(i+1).getPoint());
        }
        return distance;
    }

    public double getSpeed () {
        return mySpeed;
    }
    public void setSpeed(double speed){
        mySpeed=speed;
    }
    public double getRemainingDistance(){
        return myRemainingDistance;
    }
    public void setRoute(BackendRoute route){
        myRoute = route.getPoints();
        myRemainingDistance = calculateTotalDistance(myRoute);
    }
    public String toString(){
        return myString;
    }
    protected void move (BaseActor a, Point2D point) {
        CenteredImageView actor = a.getNode();
        actor.setXCenter(point.getX());
        actor.setYCenter(point.getY());
    }
    @Override
    public Set<Class<? extends BaseActor>> getType () {
        return null;
    }
}
