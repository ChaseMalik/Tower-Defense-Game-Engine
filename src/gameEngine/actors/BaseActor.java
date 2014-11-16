package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author $cotty $haw
 *
 */
public class BaseActor extends ImageView {
    
    protected List<IBehavior> myBehaviors;
    
    public BaseActor(){
        
    }
    public BaseActor (List<IBehavior> behaviors, Image image) {
        myBehaviors=behaviors;
        this.setImage(image);
    }
    
    /**
     * Updates the actor
     */
    public void update (){
        for(IBehavior behavior: myBehaviors){
            behavior.execute(this);
        }
        
    }
    
}
