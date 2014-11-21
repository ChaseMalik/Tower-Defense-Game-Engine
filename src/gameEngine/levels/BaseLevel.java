package gameEngine.levels;

import gameEngine.actors.BaseActor;
import java.util.HashMap;
import java.util.Map;

public class BaseLevel {
    
    private Map<BaseActor, Integer> myEnemiesMap = new HashMap<BaseActor, Integer>();
    private int myLength;
    
    
    public Map<BaseActor, Integer> getMap() {
        return myEnemiesMap;
    }
    
    public void setLength(int i){
        myLength = i;
    }
    
}
