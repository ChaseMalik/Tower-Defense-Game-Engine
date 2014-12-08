package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import java.util.List;

/**
 * Null behavior so that the authoring environment can allow users to choose no behavior
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class NullBehavior implements IBehavior {

    public NullBehavior(List<Double> list){
        
    }
    public NullBehavior(double n){
        
    }
    @Override
    public void execute (BaseActor actor) {
        // nothing
    }

    @Override
    public IBehavior copy () {
        return new NullBehavior(null);
    }



}
