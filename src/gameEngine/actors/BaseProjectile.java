package gameEngine.actors;

import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.util.HashMap;
import java.util.Map;

/**
 * Base Projectile extends Base Actor.  This class allows for the constructor of a base projectile
 * to only take in one behavior - its movement - in addition to its pertinent information
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class BaseProjectile extends BaseActor{

    public static final String MOVEMENT = "movement";
    
    public BaseProjectile (BaseMovementBehavior move) {
        Map<String, IBehavior> behaviors=new HashMap<>();
        behaviors.put(MOVEMENT,move);
        myBehaviors=behaviors;
    }

    public double getMySpeed () {
        // TODO Auto-generated method stub
        return ((BaseMovementBehavior)myBehaviors.get(MOVEMENT)).getSpeed();
    }
    public void setMove(BaseMovementBehavior move){
        myBehaviors.put(MOVEMENT, move);
    }

}
