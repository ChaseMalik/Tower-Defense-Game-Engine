package gameEngine.actors;

import gameAuthoring.scenes.actorBuildingScenes.ActorBuildingScene;
import gameEngine.actors.behaviors.BaseEffectBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import utilities.StringToImageViewConverter;
import utilities.errorPopup.ErrorPopup;
import javafx.scene.Node;
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
public abstract class BaseActor extends Observable {
    protected Map<String, IBehavior> myBehaviors;
    protected String myName;
    protected transient ImageView myNode;
    protected InfoObject myInfo;
    protected double myRange;
    protected String myImagePath;
    protected transient Set<Class<? extends BaseActor>> myTypes;
    protected Set<BaseEffectBehavior> myEffects;
    private boolean myIsRemovable;
    protected List<IBehavior> myDebuffs;
    public BaseActor () {

    }

    public BaseActor (Map<String, IBehavior> behaviors, String imageName, String name, double range) {
        myName = name;
        myBehaviors = behaviors;
        myImagePath = imageName;
        myRange = range;
        myDebuffs=new ArrayList<>();
        myEffects=new HashSet<>();
        myTypes = new HashSet<>();
        myIsRemovable = false;
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
        for(IBehavior debuff: myDebuffs){
            debuff.execute(this);
        }
        for (String s : myBehaviors.keySet()) {
            myBehaviors.get(s).execute(this);
        }

    }
    public void addDebuff(IBehavior debuff){
        myDebuffs.add(debuff);
    }
    public void removeDebuff(IBehavior debuff){
        myDebuffs.remove(debuff);
    }
    protected void makeNode () {
        int[] array = getSize();
        myNode = StringToImageViewConverter.getImageView(array[0],
                                                         array[1],
                                                         myImagePath);
    }
    protected abstract int[] getSize();

    /**
     * Copies the current actor to create another one
     * This is used when creating x amount of enemies of the same type on a specific level
     * The copy is created by copying all of the behaviors and creating a new BaseActor object
     * 
     * @return
     */

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

    public abstract Node getRange ();
    
    public double getRangeProperty(){
        return myRange;
    }

    public List<BaseActor> getEnemiesInRange () {
        return myInfo.getEnemiesInRange();
    }

    public List<BaseActor> getTowersInRange () {
        return myInfo.getTowersInRange();
    }
    public InfoObject getInfoObject(){
        return myInfo;
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
