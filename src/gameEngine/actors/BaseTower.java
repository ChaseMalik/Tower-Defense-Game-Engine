package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.Map;

public class BaseTower extends RealActor {

    private BaseTower myUpgrade;

    
    public BaseTower(Map<String,IBehavior> behaviors, String image, String name, double range, BaseTower upgrade, BaseProjectile projectile){
        super(behaviors,image,name, range,projectile);
        myUpgrade = upgrade;
    }
}
