package gameEngine.actors.behaviors;

import java.util.List;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;

/**
 * @author $cotty $haw
 *
 */
public abstract class BaseMovementBehavior implements IBehavior {

    protected List<VisibilityPoint> myRoute;
    protected double mySpeed;
    protected BackendRoute myBackendRoute;
    
    public BaseMovementBehavior(BackendRoute route, double speed){
        myBackendRoute = route;
        myRoute = route.getPoints();
        mySpeed = speed;
    }
}
