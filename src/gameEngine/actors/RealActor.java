package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.List;
import java.util.Map;

public abstract class RealActor extends BaseActor{
    protected BaseProjectile myProjectile;
    public RealActor(Map<String,IBehavior> behaviors, String image, String name, double range){
        super(behaviors,image,name, range);
        myProjectile=null;
    }    
    public RealActor(Map<String,IBehavior> behaviors, String image, String name, double range, BaseProjectile projectile){
        super(behaviors,image,name, range);
        myProjectile=projectile;
    }
    
    public BaseProjectile getProjectile(){
        return myProjectile;
    }
    public void spawnProjectile(List<BaseActor> projectile){
        setChanged();
        notifyObservers(projectile);
    }

}
