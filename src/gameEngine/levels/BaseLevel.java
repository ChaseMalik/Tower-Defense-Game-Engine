package gameEngine.levels;

import gameAuthoring.scenes.levelBuilding.EnemyCountPair;
import gameEngine.actors.BaseActor;
import java.util.ArrayList;
import java.util.List;

public class BaseLevel {

    private List<EnemyCountPair> myEnemyCountPairs = new ArrayList<EnemyCountPair>();
    private int myDurationSeconds;
    
    public boolean validateTower(BaseActor tower, double x, double y){
        //TODO: change
        return true;
    }
    
    public void addEnemyCountPair(EnemyCountPair pair) {
        for(EnemyCountPair p:myEnemyCountPairs) {
            if(p.getMyEnemy().equals(pair.getMyEnemy())) {
                p.setMyCount(pair.getMyNumEnemies());
                return;
            }
        }
        myEnemyCountPairs.add(pair);
    }
    
    public List<EnemyCountPair> getEnemyCountPairs() {
        return myEnemyCountPairs;
    }
    
    public void setDuration(int i){
        myDurationSeconds = i;
    }
    
    public int getDuration() {
    	return myDurationSeconds;
    }
}
