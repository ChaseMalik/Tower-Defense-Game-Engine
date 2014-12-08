package gameEngine.actors.behaviors;

import java.util.List;

import gameEngine.ManagerInterface.GoldUpdate;
import gameEngine.actors.BaseActor;

public class GoldGenEffect extends BaseEffect{
    private double myGold;
    
    public GoldGenEffect(List<Double> list){
        super(list);
        myGold=list.get(1);
    }
    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return new GoldGenEffect(myList);
    }

    @Override
    public void during (BaseActor actor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void start (BaseActor actor) {
        // TODO Auto-generated method stub
        actor.changeAndNotify(new GoldUpdate(myGold));
    }

    @Override
    public void end (BaseActor actor) {
        // TODO Auto-generated method stub
        myDuration=myInitialDuration;
    }

}
