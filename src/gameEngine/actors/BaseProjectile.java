package gameEngine.actors;

import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseProjectile extends BaseActor{

    public static final String MOVEMENT = "movement";
    private ProjectileInfo myInfo;
    public BaseProjectile (ProjectileInfo info, BaseMovementBehavior move) {
        myInfo=info;
        Map<String, IBehavior> behaviors=new HashMap<>();
        behaviors.put(MOVEMENT,move);
        myBehaviors=behaviors;
    }
    public ProjectileInfo getInfo(){
        return myInfo;
    }
}
