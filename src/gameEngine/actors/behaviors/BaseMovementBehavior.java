package gameEngine.actors.behaviors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;

/**
 * @author $cotty $haw
 *
 */
public abstract class BaseMovementBehavior implements IBehavior {

    protected BackendRoute myRoute;
    protected double mySpeed;
    
    public BaseMovementBehavior(BackendRoute route, double speed){
        myRoute = route;
        mySpeed = speed;
    }
}
