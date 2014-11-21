package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.Map;
import javafx.scene.image.Image;

public class BaseEnemy extends RealActor {
    

    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range, BaseProjectile projectile) {
        super(behaviors, image, name, range,projectile);
    }
    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range) {
        super(behaviors, image, name, range);
    }
}
