package gameEngine.actors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.util.List;
import java.util.Map;

public class BaseEnemy extends RealActor {
    
    private List<BackendRoute> myRoutes;
    private int myBounty;
    
    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range, int bounty, ProjectileInfo projectile, List<BackendRoute> route) {
        super(behaviors, image, name, range,projectile);
        //REPEATED CODE
        myRoutes = route;
        myBounty = bounty;
        ((BaseMovementBehavior)behaviors.get("movement")).setRoute((route.get((int)(Math.random()*route.size()))));
    }
    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range, int bounty, List<BackendRoute> route) {
        super(behaviors, image, name, range);
        //REPEATED CODE
        myRoutes=route;
        myBounty = bounty;
        ((BaseMovementBehavior)behaviors.get("movement")).setRoute((route.get((int)(Math.random()*route.size()))));
    }
    
    @Override
    public BaseActor copy(){
        Map<String, IBehavior> cBehaviors=copyBehaviors();
        BaseEnemy e = new BaseEnemy(cBehaviors, myImagePath, myName, myRange, myBounty, myProjectile, myRoutes);
        e.getNode().setVisible(false);
        return e;
    }
    
    @Override
    protected int[] getSize () {
        return new int[]{50,50};
    }
    public int getBounty(){
        return myBounty;
    }
    @Override
    public void died(double flag){
        myIsRemovable=true;
        this.setChanged();
        this.notifyObservers(new Double(myBounty*flag));
            
    }
}
