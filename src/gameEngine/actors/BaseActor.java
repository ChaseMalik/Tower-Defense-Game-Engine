package gameEngine.actors;

import gameAuthoring.scenes.actorBuildingScenes.ActorBuildingScene;
import gameEngine.actors.behaviors.BaseEffectBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import utilities.StringToImageViewConverter;
import utilities.errorPopup.ErrorPopup;
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
    protected String myImagePath;
    private transient Set<Class<? extends BaseActor>> myTypes;
    private Set<BaseEffectBehavior> myEffects;
    private boolean myIsRemovable;

    public BaseActor () {

    }

    public BaseActor (Map<String, IBehavior> behaviors, String imageName, String name, double range) {
        myName = name;
        myBehaviors = behaviors;
        myImagePath = imageName;
        myRange = range;
        myTypes = new HashSet<>();
        for (String s : behaviors.keySet()) {
            if (behaviors.get(s).getType() != null) {
                myTypes.addAll(behaviors.get(s).getType());
            }
        }
        makeNode();
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

    private void makeNode () {
        myNode = StringToImageViewConverter.getImageView(ActorBuildingScene.ACTOR_IMG_WIDTH,
                                                         ActorBuildingScene.ACTOR_IMG_WIDTH,
                                                         myImagePath);
    }

    /**
     * Copies the current actor to create another one
     * This is used when creating x amount of enemies of the same type on a specific level
     * The copy is created by copying all of the behaviors and creating a new BaseActor object
     * 
     * @return
     */
    public BaseActor copy () {
        Map<String, IBehavior> clonedBehaviors = copyBehaviors();
        BaseActor a = new BaseActor(clonedBehaviors, myImagePath, myName, myRange);
        a.makeNode();
        myNode.setVisible(false);
        return a;
    }

    protected Map<String, IBehavior> copyBehaviors () {
        Map<String, IBehavior> clonedBehaviors = new HashMap<>();
        for (String s : myBehaviors.keySet()) {
            clonedBehaviors.put(s, myBehaviors.get(s).copy());
        }
        return clonedBehaviors;
    }

    public IBehavior getBehavior (String s) {
        return myBehaviors.get(s);
    }

    public void addEffect (BaseEffectBehavior effect) {
        if (myEffects.add(effect)) {
            effect.performEffect(this);
        }
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

    public ImageView getNode () {
        return myNode;
    }

    public String getImagePath () {
        return myImagePath;
    }

    public double getRange () {
        return myRange;
    }

    public List<BaseActor> getEnemiesInRange () {
        return myInfo.getEnemiesInRange();
    }

    public List<BaseActor> getTowersInRange () {
        return myInfo.getTowersInRange();
    }

    public Collection<Class<? extends BaseActor>> getTypes () {
        return myTypes;
    }

    public void setRange (double d) {
        myRange = d;
    }
    
    public void died() {
        myIsRemovable = true;
    }
    public boolean isDead(){
        return myIsRemovable;
    }
}
