package gameEngine.levels;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseTower;
import java.util.HashMap;
import java.util.Map;

public class BaseLevel {

    private Map<BaseActor, Integer> myEnemiesMap = new HashMap<BaseActor, Integer>();
    private int myLength;
    private Map<String, BaseActor> myPrototypeTowers;
    
    private boolean validateTower(BaseActor tower, double x, double y){
        //TODO: change
        return true;
    }
    
    public BaseTower createTower(String identifier, double x, double y){
        BaseActor prototypeTower = myPrototypeTowers.get(identifier);
        if(prototypeTower == null || validateTower(prototypeTower, x, y)){
            return null;
        }        
        BaseTower tower = (BaseTower)prototypeTower.copy();
        tower.getNode().setX(x);
        tower.getNode().setY(y);
        return tower;
    }
    
    public Map<BaseActor, Integer> getMap() {
        return myEnemiesMap;
    }
    
    public void setLength(int i){
        myLength = i;
    }
    
}
