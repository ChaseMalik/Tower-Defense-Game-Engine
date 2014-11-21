package gameAuthoring.dataObjects;

import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.util.HashMap;
import java.util.Map;

public class BaseProjectileData extends BaseActorData {

    public static final String MOVEMENT = "movement";
    private ProjectileData myInfo;
    
    public BaseProjectileData (ProjectileData info, BaseMovementBehavior move) {
        myInfo=info;
        Map<String, IBehavior> behaviors=new HashMap<>();
        behaviors.put(MOVEMENT, move);
        myBehaviors = behaviors;
    }
    
    public ProjectileData getInfo(){
        return myInfo;
    }
}
