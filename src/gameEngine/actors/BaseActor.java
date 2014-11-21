package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
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
public class BaseActor extends Observable {
    protected Map<String, IBehavior> myBehaviors;
    protected String myName;
    protected transient ImageView myNode;
    protected InfoObject myInfo;
    protected double myRange;
    protected String myImageName;

    public BaseActor () {

    }

    public BaseActor (Map<String, IBehavior> behaviors, String imageName, String name, double range) {
        myName = name;
        myBehaviors = behaviors;
        myImageName = imageName;
        myRange = range;
    }

    /**
     * Updates the actor by looping over all of its behaviors and performing them
     */
    public void update (InfoObject info) {
        myInfo = info;
        for (String s : myBehaviors.keySet()) {
            myBehaviors.get(s).execute(this);
        }

    }

    private void makeNode(){
        myNode = new ImageView(myImageName);
    }
    /**
     * Copies the current actor to create another one
     * This is used when creating x amount of enemies of the same type on a specific level
     * The copy is created by copying all of the behaviors and creating a new BaseActor object
     * 
     * @return
     */
    public BaseActor copy () {
        Map<String, IBehavior> clonedBehaviors = new HashMap<>();
        for (String s : myBehaviors.keySet()) {
            clonedBehaviors.put(s, myBehaviors.get(s).copy());
        }
        BaseActor a = new BaseActor(clonedBehaviors, myImageName, myName,myRange);
        a.makeNode();
        return a;
    }

    public IBehavior getBehavior (String s) {
        return myBehaviors.get(s);
    }

    @Override
    public String toString () {
        return myName;
    }

    public double getX () {
        return myNode.getX();
    }

    public double getY () {
        return myNode.getY();
    }

    public ImageView getNode(){
        return myNode;
    }
    
    public double getRange() {
        return myRange;
    }
    public List<BaseActor> getEnemiesInRange(){
        return myInfo.getEnemiesInRange();
    }
    public List<BaseActor> getTowersInRange(){
        return myInfo.getTowersInRange();
    }
}
