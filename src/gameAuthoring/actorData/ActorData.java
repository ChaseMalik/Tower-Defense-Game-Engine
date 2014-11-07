package gameAuthoring.actorData;

import gameAuthoring.mainclasses.Data;
import java.util.HashMap;

public abstract class ActorData extends Data {
    
    private static final String SPEED = "speed";
    private static final String IMAGE_PATH = "imagePath";
    
    protected HashMap<String, String> myDataMap;
    
    public ActorData(String speed, String imagePath){
        myDataMap = new HashMap<String, String>();
        myDataMap.put(SPEED, speed);
        myDataMap.put(IMAGE_PATH, imagePath);
    }
}
