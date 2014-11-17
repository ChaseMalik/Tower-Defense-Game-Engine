package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;

public class NullBehavior implements IBehavior {

    @Override
    public void execute (BaseActor actor) {
        // nothing
    }

    @Override
    public IBehavior copy () {
        return new NullBehavior();
    }

}
