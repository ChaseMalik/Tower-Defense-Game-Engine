package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import gameEngine.backendExceptions.BackendException;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
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
public class BaseActor extends ImageView {

    protected List<IBehavior> myBehaviors;

    public BaseActor () {

    }

    public BaseActor (List<IBehavior> behaviors, Image image) {
        myBehaviors = behaviors;
        this.setImage(image);
        this.setVisible(false);
    }

    /**
     * Updates the actor
     */
    public void update () {
        for (IBehavior behavior : myBehaviors) {
            behavior.execute(this);
        }

    }

    public BaseActor copy () {
        List<IBehavior> clonedBehaviors = new ArrayList<>();
        for (IBehavior behavior : myBehaviors) {
            clonedBehaviors.add(behavior.copy());
        }
        return new BaseActor(clonedBehaviors, this.getImage());
    }

}
