package gameEngine.levels;

import gameEngine.actors.BaseActor;
import java.util.HashMap;
import java.util.Map;

public class BaseLevel {
    
    private Map<BaseActor, Integer> numEnemiesMap = new HashMap<BaseActor, Integer>();
    private Map<String, BaseActor> myPrototypeTowers;
    
    private boolean validateTower(BaseActor tower, double x, double y){
        //TODO: change
        return true;
    }
    
    public BaseActor createTower(String identifier, double x, double y){
        BaseActor prototypeTower = myPrototypeTowers.get(identifier);
        if(prototypeTower == null || validateTower(prototypeTower, x, y)){
            return null;
        }        
        BaseActor tower = prototypeTower.copy();
        tower.setX(x);
        tower.setY(y);
        return tower;
    }
    
    public Map<BaseActor, Integer> getMap() {
        return numEnemiesMap;
    }
    
}
