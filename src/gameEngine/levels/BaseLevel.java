package gameEngine.levels;

import gameEngine.actors.BaseActor;
import java.util.HashMap;
import java.util.Map;

public class BaseLevel {
    
    private Map<BaseActor, Integer> numEnemiesMap = new HashMap<BaseActor, Integer>();
    
    public Map<BaseActor, Integer> getMap() {
        return numEnemiesMap;
    }
    
}
