package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.List;
import java.util.Map;

public abstract class RealActor extends BaseActor{
    protected ProjectileInfo myProjectile;
    public RealActor(Map<String,IBehavior> behaviors, String image, String name, double range){
        super(behaviors,image,name, range);
        myProjectile=null;
    }    
    public RealActor(Map<String,IBehavior> behaviors, String image, String name, double range, ProjectileInfo projectile){
        super(behaviors,image,name, range);
        myProjectile=projectile;
    }
    
    public ProjectileInfo getProjectile(){
        return myProjectile;
    }
    public void spawnProjectile(List<BaseActor> projectile){
        setChanged();
        notifyObservers(projectile);
    }

}
