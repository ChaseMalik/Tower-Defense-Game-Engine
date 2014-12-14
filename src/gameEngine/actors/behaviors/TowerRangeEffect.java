package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import java.util.List;

public class TowerRangeEffect extends BaseEffect {
    private double myRange;
    private double myMultiplier;
    public TowerRangeEffect(List<Double> list){
        super(list);
        myMultiplier=list.get(0)/100.0;
        myRange=list.get(1);
        
        myString="towerRangeEffect";
    }


    @Override
    public IBehavior copy () {
        return new TowerRangeEffect(myList);
    }
    @Override
    public void during (BaseActor actor) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void start (BaseActor actor) {
        // TODO Auto-generated method stub
        for(BaseActor a : actor.getTowersInRange(myRange)){
            a.setRange(a.getRangeProperty()*myMultiplier);
        }
    }
    @Override
    public void end (BaseActor actor) {
        // TODO Auto-generated method stub
        
    }



}
