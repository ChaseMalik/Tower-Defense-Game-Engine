package gameEngine.actors;

import java.util.Observable;

public abstract class BaseActor extends Observable {
    
    /**
     * Updates the actor's position
     */
    public abstract void update ();
    
    /**
     * Executes the actor's on-death actions
     */
    public abstract void onDeath ();

}
