package gameAuthoring.data;

import org.w3c.dom.Node;

public class EnemyData extends ActorData {
    
    private static final String DAMAGE = "damage";
    private static final String HEALTH = "health";
    
    public EnemyData(String speed, String imagePath, String damage, String health) {
        super(speed, imagePath);
        myDataMap.put(DAMAGE, damage);
        myDataMap.put(HEALTH, health);
    }

    @Override
    public Node getJsonRepresentation () {
        // TODO Auto-generated method stub
        return null;
    }
}
