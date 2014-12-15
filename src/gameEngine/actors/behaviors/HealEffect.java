package gameEngine.actors.behaviors;

import java.util.List;

import gameEngine.ManagerInterface.HealthUpdate;
import gameEngine.actors.BaseActor;

public class HealEffect extends BaseEffect{
    
    private double myHeal;
    public HealEffect(List<Double> list){
        super(list);
        myHeal=list.get(1);
    }
    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return new HealEffect(myList);
    }

    @Override
    public void during (BaseActor actor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void start (BaseActor actor) {
        // TODO Auto-generated method stub
        actor.changeAndNotify(new HealthUpdate(myHeal));
    }

    @Override
    public void end (BaseActor actor) {
        // TODO Auto-generated method stub
        myDuration=myInitialDuration+1;
    }

}
