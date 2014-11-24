package gameEngine.actors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.util.List;
import java.util.Map;

public class BaseEnemy extends RealActor {
    
    private List<BackendRoute> myRoutes;
    
    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range, ProjectileInfo projectile, List<BackendRoute> route) {
        super(behaviors, image, name, range,projectile);
        myRoutes=route;
        ((BaseMovementBehavior)behaviors.get("movement")).setRoute((route.get((int)(Math.random()*route.size()))));
    }
    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range, List<BackendRoute> route) {
        super(behaviors, image, name, range);
        myRoutes=route;
        ((BaseMovementBehavior)behaviors.get("movement")).setRoute((route.get((int)(Math.random()*route.size()))));
    }
    
    public BaseActor copy(){
        Map<String, IBehavior> cBehaviors=copyBehaviors();
        return new BaseEnemy(cBehaviors, myImagePath, myName, myRange, myProjectile, myRoutes);
    }
    @Override
    protected int[] getSize () {
        return new int[]{50,50};
    }
}
