package gameAuthoring.actorData;

import java.util.List;
import org.w3c.dom.Node;

public class TowerData extends ActorData {

    private static final String DAMAGE = "damage";
    private static final String HEALTH = "health";
    
    private List<String> enemiesCanShoot;
    
    public TowerData(String speed, String imagePath, String damage, String health) {
        super(speed, imagePath);
        myDataMap.put(DAMAGE, damage);
        myDataMap.put(HEALTH, health);
    }
    
    public void addEnemyTowerCanShoot(String enemyType) {
        enemiesCanShoot.add(enemyType);
    }

    @Override
    public Node getJsonRepresentation () {
        // TODO Auto-generated method stub
        return null;
    }
}
