package gameEngine.actors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;
import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;

import java.util.List;
import java.util.Map;

public class BaseEnemy extends RealActor {
    
    private List<BackendRoute> myRoutes;
    private int myBounty;
    private VisibilityPoint myStart;
    private VisibilityPoint myGoal;
    
    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range, int bounty, ProjectileInfo projectile, List<BackendRoute> route) {
        super(behaviors, image, name, range,projectile);
        initializeEnemy(behaviors, bounty, route);
    }
    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range, int bounty, List<BackendRoute> route) {
        super(behaviors, image, name, range);
        initializeEnemy(behaviors, bounty, route);
    }
    
    private void initializeEnemy(Map<String, IBehavior> behaviors, int bounty, List<BackendRoute> route) {
    	myRoutes=route;
        myBounty = bounty;
        BackendRoute selectedRoute = route.get((int)(Math.random()*route.size()));
        ((BaseMovementBehavior)behaviors.get("movement")).setRoute(selectedRoute);
        List<VisibilityPoint> routePoints = selectedRoute.getPoints();
        myStart = routePoints.get(0);
        myGoal = routePoints.get(routePoints.size() -1);
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
    
    public VisibilityPoint getStart() {
    	return myStart;
    }
    
    public VisibilityPoint getGoal() {
    	return myGoal;
    }
}
