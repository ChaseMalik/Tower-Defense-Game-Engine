package gameEngine.actors.behaviors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;
import gameEngine.actors.BaseActor;
import java.util.List;
import javafx.geometry.Point2D;
import utilities.JavaFXutilities.imageView.CenteredImageView;


/**
 *  Behavior that defines the movement of an actor
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public abstract class BaseMovementBehavior implements IBehavior {
    
    protected List<Double> myList;
    protected String myString;
    protected List<VisibilityPoint> myRoute;
    protected double mySpeed;
    protected double myRemainingDistance;
    protected int myIndex;

    BaseMovementBehavior (List<Double> list) {
        mySpeed=list.get(0);
        myList=list;
        myIndex = 0;
    }

    public BaseMovementBehavior (double speed) {
        mySpeed = speed;
        myString="movement";
    }
    public BaseMovementBehavior (double speed, BackendRoute route) {
        mySpeed = speed;
        myRoute=route.getPoints();
    }
    /*
     * calculate distance of route
     */
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
    /*
     * move the actor
     */
    protected void move (BaseActor a, Point2D point) {
        CenteredImageView actor = a.getNode();
        actor.setXCenter(point.getX());
        actor.setYCenter(point.getY());
    }
    
    protected void moveTowards(BaseActor a, Point2D current, Point2D target){
        myRemainingDistance-=mySpeed;
        move(a,current.add(target.subtract(current).normalize().multiply(mySpeed)));
    }
    /*
     * finds next target and moves toward it
     */
    protected void findNextTarget(BaseActor actor, List<Point2D> route){
        Point2D current = new Point2D(actor.getX(), actor.getY());
        Point2D destination = route.get(myIndex);
        double distance = mySpeed;

        while (distance > destination.distance(current)) {
            myIndex++;
            if (myIndex == route.size()) {
                actor.died();
                return;
            }
            distance -= destination.distance(current);
            current = new Point2D(destination.getX(), destination.getY());
            destination = route.get(myIndex);
        }
        moveTowards(actor,current,destination);
    }

}
