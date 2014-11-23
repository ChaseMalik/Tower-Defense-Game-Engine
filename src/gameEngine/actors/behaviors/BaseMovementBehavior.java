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

    protected List<VisibilityPoint> myRoute;
    protected double mySpeed;
    protected List<BackendRoute> myOptions;
    public BaseMovementBehavior (List<BackendRoute> routeOptions, double speed) {
        myOptions = routeOptions;
        int index = new Random().nextInt(myOptions.size()); // should declare new utilities elsewhere
        myRoute = routeOptions.get(index).getPoints();
        mySpeed = speed;
    }

    public double getSpeed () {
        return mySpeed;
    }
    @Override
    public Set<Class<? extends BaseActor>> getType () {
        // TODO Auto-generated method stub
        return null;
    }
}
