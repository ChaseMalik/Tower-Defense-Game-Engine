package gameEngine.actors.behaviors;

import java.util.List;
import gameEngine.healthUpdate;
import gameEngine.actors.BaseActor;

public class healBehavior extends BaseEffect{
    
    private double myHeal;
    public healBehavior(List<Double> list){
        super(list);
        myHeal=list.get(1);
    }
    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return new healBehavior(myList);
    }

    @Override
    public void during (BaseActor actor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void start (BaseActor actor) {
        // TODO Auto-generated method stub
        actor.changeAndNotify(new healthUpdate(myHeal));
    }

    @Override
    public void end (BaseActor actor) {
        // TODO Auto-generated method stub
        myDuration=myInitialDuration;
    }

}
