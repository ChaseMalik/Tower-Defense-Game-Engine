package gameEngine.actors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;
import gameEngine.ManagerInterface.GoldUpdate;
import gameEngine.ManagerInterface.HealthUpdate;
import gameEngine.actors.behaviors.BaseMovementBehavior;
import gameEngine.actors.behaviors.IBehavior;
import java.util.List;
import java.util.Map;
import java.util.Random;
/**
 * 
 * 
 * 
 * @author Chase Malik, Timesh Patel
 *
 */
public class BaseEnemy extends RealActor {
    
    private List<BackendRoute> myRoutes;
    private int myDamage;
    private VisibilityPoint myStart;
    private VisibilityPoint myGoal;
    private long MY_SEED=828;
    private Random myRandom;
    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range, int damage, ProjectileInfo projectile, List<BackendRoute> route) {
        super(behaviors, image, name, range,projectile);
        myRandom=new Random(MY_SEED);
        initializeEnemy(behaviors, damage, route);
    }
    public BaseEnemy (Map<String, IBehavior> behaviors, String image, String name, double range, int damage, List<BackendRoute> route) {
        super(behaviors, image, name, range);
        myRandom=new Random(MY_SEED);
        initializeEnemy(behaviors, damage, route);
    }
    
    private void initializeEnemy(Map<String, IBehavior> behaviors, int damage, List<BackendRoute> route) {
    	
        myRoutes=route;
        myDamage = damage;
        BackendRoute selectedRoute = route.get(myRandom.nextInt(route.size()));
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
    public void died(){
        this.changeAndNotify(new HealthUpdate(-1*myDamage));
        myIsRemovable=true;
            
    }
    @Override 
    public void killed(){
        this.changeAndNotify(new GoldUpdate(myBounty));
        myIsRemovable=true;
    }
    
    public VisibilityPoint getStart() {
    	return myStart;
    }
    
    public VisibilityPoint getGoal() {
    	return myGoal;
    }
}
