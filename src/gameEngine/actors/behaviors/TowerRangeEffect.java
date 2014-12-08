package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TowerRangeEffect extends RangeEffect {
    private int myRange;
    public TowerRangeEffect(List<Double> list){
        super(list);
    }
    public TowerRangeEffect (double change) {
        super(change);
        myString="towerRangeEffect";
    }

    @Override
    public void execute (BaseActor actor) {
       for(BaseActor a :actor.getTowersInRange(myRange)){
           
           a.addEffect(this);
       }
    }

    @Override
    public IBehavior copy () {
        return new TowerRangeEffect(myList);
    }



}
