package gameAuthoring.dataObjects;

import gameEngine.actors.behaviors.IBehavior;
import java.util.Map;

public class BaseTowerData extends BaseActorData {

    private BaseTowerData myUpgrade;
    private BaseProjectileData myProjectile;
    
    public BaseTowerData(Map<String,IBehavior> behaviors, String imageFileLoc, String name, double range, BaseTowerData upgrade, BaseProjectileData projectile){
        super(behaviors,imageFileLoc, name, range);
        myUpgrade = upgrade;
        myProjectile = projectile;
    }
}
