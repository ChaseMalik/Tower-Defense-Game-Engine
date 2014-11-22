package gameEngine.actors.behaviors;

import java.util.Set;
import gameEngine.actors.BaseActor;

/**
 * Interface that defines the behaviors of actors in the game
 * All types of behaviors execute on a specific actor
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public interface IBehavior {
    
    /**
     * Execute method that executes the specific behavior on the given actor
     * @param actor that is executed on
     */
    public void execute(BaseActor actor);

    /**
     * Returns a copy of the behavior, which allows for copying entire actors
     * @return copy of the behavior
     */
    public IBehavior copy ();

    public Set<Class<? extends BaseActor>> getType ();
    
}
