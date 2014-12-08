package gameEngine.actors.behaviors;

import java.util.List;
import gameEngine.actors.BaseActor;

public abstract class RangeEffect extends BaseEffectBehavior {

    public RangeEffect (double change) {
        super(change);
    }
    public RangeEffect(List<Double> list){
        super(list);
    }
    @Override
    public void performEffect (BaseActor a) {
        a.setRange(a.getRangeProperty()* myChange);
    }
}
