package gameEngine.actors;

import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;

/**
 * Base Projectile extends Base Actor.  This class allows for the constructor of a base projectile
 * to only take in one behavior - its movement - in addition to its pertinent information
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class BaseProjectile extends BaseActor{

    public static final String MOVEMENT = "movement";
    private ProjectileInfo myInfo;
    public BaseProjectile (ProjectileInfo info) {
        myImagePath=info.getImage();
        makeNode();
        myNode.setVisible(false);
        Map<String, IBehavior> behaviors=new HashMap<>();
        behaviors.put(MOVEMENT,myInfo.getMove());
        myBehaviors=behaviors;
    }
    @Override
    protected int[] getSize () {
        return new int[]{10,10};
    }

    public void setMove(BaseMovementBehavior move){
        myBehaviors.put(MOVEMENT, move);
    }
    public ProjectileInfo getInfo(){
        return myInfo;
    }
    @Override
    public Node getRange () {
        // TODO Auto-generated method stub
        return myNode;
    }

}
