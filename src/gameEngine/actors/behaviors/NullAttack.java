package gameEngine.actors.behaviors;

import java.util.List;
import gameEngine.actors.BaseActor;

public class NullAttack extends BaseAttack {

    public NullAttack (List<Double> list) {
        super(list);
    }

    @Override
    public IBehavior copy () {
        return new NullAttack(myList);
    }

    @Override
    protected void performAttack (BaseActor actor) {
        // nothing
    }

}
