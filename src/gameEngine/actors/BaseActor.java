package gameEngine.actors;

import gameEngine.actors.behaviors.IAct;
import gameEngine.actors.behaviors.IBehavior;
import gameEngine.backendExceptions.BackendException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Node;

/**
 * @author $cotty $haw
 *
 */
public abstract class BaseActor extends Node implements Observer { //extend Observable or not?
    
    private int myID;
    private List<Behavior> myBehaviors;
    
    public BaseActor (int ID, List<Behavior> list) throws BackendException {
        myID = ID;
        myBehaviors=list;
    }
    
    /**
     * Updates the actor's position
     */
    public void update (){
        for(Behavior behavior: myBehaviors){
            behavior.onUpdate();
        }
        
    }
    
    /**
     * Executes the actor's on-death actions
     */
    public abstract void onDeath ();

}
