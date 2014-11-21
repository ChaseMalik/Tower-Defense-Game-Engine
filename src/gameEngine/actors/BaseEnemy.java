package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.Map;
import javafx.scene.image.Image;

public class BaseEnemy extends BaseActor {
    
    public BaseEnemy (Map<String, IBehavior> behaviors, Image image, String name, double range) {
        super(behaviors, image, name, range);
    }
}
