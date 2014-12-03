package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.Map;


public class BaseTower extends RealActor {
    
    private int myBuyCost;
    private int mySellCost;

    public BaseTower (Map<String, IBehavior> behaviors,
                      String image,
                      String name,
                      double range,
                      int buyCost,
                      int sellCost,
                      ProjectileInfo projectile) {
        super(behaviors, image, name, range, projectile);
        myBuyCost = buyCost;
        mySellCost = sellCost;
    }
    @Override
    protected int[] getSize () {
        return new int[]{75,75};
    }

    @Override
    public BaseActor copy () {
        Map<String, IBehavior> clonedBehaviors = copyBehaviors();
        return new BaseTower(clonedBehaviors, myImagePath, myName, myRange, myBuyCost, mySellCost, myProjectile);
    }
    
    public int getBuyCost () {
        return myBuyCost;
    }
    
    public int getSellCost () {
        return mySellCost;
    }
}
