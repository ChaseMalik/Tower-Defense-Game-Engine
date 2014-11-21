package gameAuthoring.dataObjects;

import gameEngine.actors.behaviors.IBehavior;
import java.util.Map;

public class BaseEnemyData extends BaseActorData {
    
    public BaseEnemyData (Map<String, IBehavior> behaviors, String imageFileLoc, String name, double range) {
        super(behaviors, imageFileLoc, name, range);
    }
}
