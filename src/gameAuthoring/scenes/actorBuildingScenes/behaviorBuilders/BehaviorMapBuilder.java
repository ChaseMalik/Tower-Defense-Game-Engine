package gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders;

import gameEngine.actors.behaviors.IBehavior;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BehaviorMapBuilder {
    public static Map<String, IBehavior> buildMap(List<BehaviorBuilder> builders) {
        Map<String, IBehavior> iBehaviorMap = new HashMap<String, IBehavior>();
        for(BehaviorBuilder builder:builders){
            IBehaviorKeyValuePair pair = builder.buildBehavior();
            iBehaviorMap.put(pair.getTypeOfBehavior(), pair.getMyIBehavior());
        }
        return iBehaviorMap;
    }
}
