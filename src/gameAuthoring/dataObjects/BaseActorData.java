package gameAuthoring.dataObjects;

import gameEngine.actors.behaviors.IBehavior;
import java.util.Map;
import java.util.Observable;


public class BaseActorData extends Observable {
    
    protected Map<String, IBehavior> myBehaviors;
    protected String myName;
    protected String myImageLocation;
    protected double myRange;

    public BaseActorData () {

    }

    public BaseActorData (Map<String, IBehavior> behaviors, String imageFileLoc, String name, double range) {
        myName = name;
        myBehaviors = behaviors;
        myImageLocation = imageFileLoc;
        myRange = range;
    }

    public IBehavior getBehavior (String s) {
        return myBehaviors.get(s);
    }

    @Override
    public String toString () {
        return myName;
    }
    
    public double getRange() {
        return myRange;
    }
}
