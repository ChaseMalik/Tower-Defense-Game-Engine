package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.Map;

public class BaseEnemy extends RealActor {
    

    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range, ProjectileInfo projectile) {
        super(behaviors, image, name, range,projectile);
    }
    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range) {
        super(behaviors, image, name, range);
    }
}
