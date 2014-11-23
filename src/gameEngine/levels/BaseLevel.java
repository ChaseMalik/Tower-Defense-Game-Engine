package gameEngine.levels;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;

import java.util.HashMap;
import java.util.Map;

public class BaseLevel {

    private Map<BaseEnemy, Integer> myEnemiesToCountMap = new HashMap<BaseEnemy, Integer>();
    private int myDurationSeconds;
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
    
    public Map<BaseEnemy, Integer> getEnemyMap() {
        return myEnemiesToCountMap;
    }
    
    public void setDuration(int i){
        myDurationSeconds = i;
    }
    
    public int getDuration() {
    	return myDurationSeconds;
    }
}
