package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;

public class AoEEffect extends BaseOnHitBehavior{
    public AoEEffect (double multiplier) {
        super(1,multiplier);
        myString="AoEEffect";
        // TODO Auto-generated constructor stub
    }


    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return new AoEEffect(myMultiplier);
    }

    @Override
    public void during (BaseActor actor) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void start (BaseActor actor) {
        // TODO Auto-generated method stub
        for(BaseActor a:actor.getEnemiesInRange(1000)){
            a.killed();
        }
    }

    @Override
    public void end (BaseActor actor) {
        // TODO Auto-generated method stub
        actor.removeDebuff(this);
    }

}
