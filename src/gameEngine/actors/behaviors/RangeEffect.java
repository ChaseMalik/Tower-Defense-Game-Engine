package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;

public abstract class RangeEffect extends BaseEffectBehavior {

    public RangeEffect (double change) {
        super(change);
    }

    @Override
    public void performEffect (BaseActor a) {
        a.setRange(a.getRangeProperty()* myChange);
    }
}
