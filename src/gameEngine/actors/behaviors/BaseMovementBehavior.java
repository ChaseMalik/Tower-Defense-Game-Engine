package gameEngine.actors.behaviors;

import java.util.List;
import java.util.Random;
import java.util.Set;
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

    public static final Random RANDOM = new Random();
    
    protected List<VisibilityPoint> myRoute;
    protected double mySpeed;
    protected List<BackendRoute> myOptions;
    protected double myRemainingDistance;

    public BaseMovementBehavior (List<BackendRoute> routeOptions, double speed) {
        myOptions = routeOptions;
        int index = RANDOM.nextInt(myOptions.size());
        myRoute = routeOptions.get(index).getPoints();
        myRemainingDistance = calculateTotalDistance(myRoute);
        mySpeed = speed;
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
    
    public double getRemainingDistance(){
        return myRemainingDistance;
    }
    
    @Override
    public Set<Class<? extends BaseActor>> getType () {
        return null;
    }
}
