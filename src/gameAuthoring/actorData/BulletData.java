package gameAuthoring.actorData;

import org.w3c.dom.Node;

public class BulletData extends ActorData {
    
    private static final String DAMAGE = "damage";
    
    public BulletData(String speed, String imagePath, String damage) {
        super(speed, imagePath);
        myDataMap.put(DAMAGE, damage);
    }

    @Override
    public Node getJsonRepresentation () {
        // TODO Auto-generated method stub
        return null;
    }
}
