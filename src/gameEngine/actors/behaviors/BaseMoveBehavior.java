package gameEngine.actors.behaviors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;

/**
 * @author $cotty $haw
 *
 */
public abstract class BaseMoveBehavior implements IBehavior {

    BackendRoute myRoute;
    double mySpeed;
    
    public BaseMoveBehavior(BackendRoute route, double speed){
        myRoute = route;
        mySpeed = speed;
    }
}
