package gameEngine.actors;

import gameEngine.actors.behaviors.IBehavior;
import java.util.Map;
import javafx.scene.image.Image;

public class BaseTower extends BaseActor {

    private BaseTower myUpgrade;
    private BaseProjectile myProjectile;
    
    public BaseTower(Map<String,IBehavior> behaviors, String image, String name, double range, BaseTower upgrade, BaseProjectile projectile){
        super(behaviors,image,name, range);
        myUpgrade = upgrade;
        myProjectile = projectile;
    }
}
