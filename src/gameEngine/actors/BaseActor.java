package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Base Actor class that represents all of the actors on the screen (enemies, towers, projectiles)
 * Stores its behaviors, image, and a name
 * On an update call, it simply loops over its current behaviors and executes them
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class BaseActor extends ImageView {
    protected Map<String,IBehavior> myBehaviors;
    protected String myName;

    public BaseActor () {

    }

    public BaseActor (Map<String,IBehavior> behaviors, Image image, String name) {
        myName = name;
        myBehaviors = behaviors;
        this.setImage(image);
        this.setVisible(false);
    }

    /**
     * Updates the actor by looping over all of its behaviors and performing them
     */
    public void update () {
        for(String s: myBehaviors.keySet()){
            myBehaviors.get(s).execute(this);
        }

    }

    /**
     * Copies the current actor to create another one
     * This is used when creating x amount of enemies of the same type on a specific level
     * The copy is created by copying all of the behaviors and creating a new BaseActor object
     * @return
     */
    public BaseActor copy () {
        Map<String, IBehavior> clonedBehaviors = new HashMap<>();
        for (String s: myBehaviors.keySet()) {
            clonedBehaviors.put(s, myBehaviors.get(s).copy());
        }
        return new BaseActor(clonedBehaviors, getImage(), myName);
    }
    
    public IBehavior getBehavior(String s){
        return myBehaviors.get(s);
    }
    
    @Override
    public String toString(){
        return myName;
    }

}
