package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.Map;


public class BaseTower extends RealActor {

    public BaseTower (Map<String, IBehavior> behaviors,
                      String image,
                      String name,
                      double range,
                      ProjectileInfo projectile) {
        super(behaviors, image, name, range, projectile);
    }

    @Override
    public BaseActor copy () {
        Map<String, IBehavior> clonedBehaviors = copyBehaviors();
        return new BaseTower(clonedBehaviors, myImagePath, myName, myRange, myProjectile);
    }
}
