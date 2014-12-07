package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;

public class AoEEffect extends BaseOnHitBehavior{
    private boolean bool;
    public AoEEffect (double multiplier) {
        super(1,multiplier);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void execute (BaseActor actor) {
        // TODO Auto-generated method stub
        if(myDuration==1){
            bool=actor.addTypes(BaseEnemy.class);
        }else{
            
        }
    }

    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void undo (BaseActor actor) {
        // TODO Auto-generated method stub
        
    }

}
