package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;
import java.util.HashSet;
import java.util.Set;

public class TowerRangeEffect extends RangeEffect {

    public TowerRangeEffect (double change) {
        super(change);
    }

    @Override
    public void execute (BaseActor actor) {
       for(BaseActor a :actor.getTowersInRange()){
           
           a.addEffect(this);
       }
    }

    @Override
    public IBehavior copy () {
        return new TowerRangeEffect(myChange);
    }

    @Override
    public Set<Class<? extends BaseActor>> getType () {
       Set<Class<? extends BaseActor>> set = new HashSet<>();
       set.add(BaseTower.class);
       return set;
    }

}
