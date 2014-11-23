package gameEngine.actors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.util.Map;

public class BaseEnemy extends RealActor {
        
    
    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range, ProjectileInfo projectile, BackendRoute route) {
        super(behaviors, image, name, range,projectile);
        ((BaseMovementBehavior)behaviors.get("movement")).setRoute(route);
    }
    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range, BackendRoute route) {
        super(behaviors, image, name, range);
        ((BaseMovementBehavior)behaviors.get("movement")).setRoute(route);
    }
}
