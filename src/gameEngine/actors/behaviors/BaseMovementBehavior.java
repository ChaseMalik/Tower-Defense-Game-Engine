package gameEngine.actors.behaviors;

import java.util.List;
import java.util.Random;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;

/**
 * @author $cotty $haw
 *
 */
public abstract class BaseMovementBehavior implements IBehavior {

    protected List<VisibilityPoint> myRoute;
    protected double mySpeed;
    protected List<BackendRoute> myOptions;
    
    public BaseMovementBehavior(List<BackendRoute> routeOptions, double speed){
        myOptions = routeOptions;
        int index = new Random().nextInt(myOptions.size()); // should declare new random elsewhere
        myRoute = routeOptions.get(index).getPoints();
        mySpeed = speed;
    }
}
