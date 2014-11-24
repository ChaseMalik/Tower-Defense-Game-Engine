package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;

public class SimpleDefense extends BaseDefendBehavior{
    public SimpleDefense(double health){
        super(health);
    }

    @Override
    public void execute (BaseActor actor) {
        for(BaseActor a: actor.getInfoObject().getProjectilesInRange()){
            if(actor.getNode().intersects(a.getRange().getBoundsInLocal())){
            myHealth--;
            a.died();
            if(myHealth<=0)
                actor.died();
        }
        }
    }

    @Override
    public IBehavior copy () {
        return new SimpleDefense(myInitialHealth);
    }
}
