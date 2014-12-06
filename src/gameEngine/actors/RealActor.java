package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

public abstract class RealActor extends BaseActor {
    protected ProjectileInfo myProjectile;
    public RealActor(Map<String,IBehavior> behaviors, String image, String name, double range){
        super(behaviors,image,name, range);
        myProjectile=null;
    }    
    public RealActor(Map<String,IBehavior> behaviors, String image, String name, double range, ProjectileInfo projectile){
        super(behaviors,image,name, range);
        myProjectile=projectile;
    }
    @Override
    public void update(InfoObject info){
        myInfo = info;
        for(String s: myDebuffs.keySet()){
            myDebuffs.get(s).execute(this);
        }
        for (String s : myBehaviors.keySet()) {
            myBehaviors.get(s).execute(this);
        }
        for(IBehavior debuff: myDebuffsToRemove){
            myDebuffs.remove(debuff.toString());
        }
        myDebuffsToRemove.clear();
        
    }
    public ProjectileInfo getProjectile(){
        return myProjectile;
    }
    public void spawnProjectile(BaseActor projectile){
        setChanged();
        notifyObservers(projectile);
    }
    public Node getRange() {
        return new Circle(myNode.getXCenter(),myNode.getYCenter(),myRange);
    }
    
    /**
     * Copies the current actor to create another one
     * This is used when creating x amount of enemies of the same type on a specific level
     * The copy is created by copying all of the behaviors and creating a new BaseActor object
     */
    public abstract BaseActor copy();
}