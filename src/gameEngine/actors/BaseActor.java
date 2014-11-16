package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import gameEngine.backendExceptions.BackendException;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author $cotty $haw
 *
 */
public abstract class BaseActor extends ImageView {
    
    private List<IBehavior> myBehaviors;
    
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
