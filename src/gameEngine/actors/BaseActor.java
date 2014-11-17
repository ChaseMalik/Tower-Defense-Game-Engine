package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * @author $cotty $haw
 *
 */
public class BaseActor extends ImageView {
    protected Map<String,IBehavior> myBehaviors;

    public BaseActor () {

    }

    public BaseActor (Map<String,IBehavior> behaviors, Image image) {
        myBehaviors = behaviors;
        this.setImage(image);
        this.setVisible(false);
    }

    /**
     * Updates the actor
     */
    public void update () {
        for(String s: myBehaviors.keySet()){
            myBehaviors.get(s).execute(this);
        }

    }

    public BaseActor copy () {
        Map<String, IBehavior> clonedBehaviors = new HashMap<>();
        for (String s: myBehaviors.keySet()) {
            clonedBehaviors.put(s, myBehaviors.get(s).copy());
        }
        return new BaseActor(clonedBehaviors, this.getImage());
    }
    
    public IBehavior getBehavior(String s){
        return myBehaviors.get(s);
    }

}
