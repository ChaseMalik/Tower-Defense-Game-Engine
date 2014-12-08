package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;

public class AoEEffect extends BaseOnHitBehavior{
    public AoEEffect (double multiplier) {
        super(1,multiplier);
        myString="AoEEffect";
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute (BaseActor actor) {
        // TODO Auto-generated method stub
        if(myDuration==1){
            for(BaseActor a:actor.getEnemiesInRange(1000)){
                a.killed();
            }
            
        }
            
        if(myDuration==0){
               actor.removeDebuff(this);
        }
        myDuration--;
    }

    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return new AoEEffect(myMultiplier);
    }

    @Override
    public void undo (BaseActor actor) {
        // TODO Auto-generated method stub
        
    }

}
