package gameEngine.actors;

import java.util.List;

public class InfoObject {

    private List<BaseEnemy> myEnemiesInRange;
    private List<BaseTower> myTowersInRange;
    
    public InfoObject(List<BaseEnemy> enemies, List<BaseTower> towers){
        myEnemiesInRange = enemies;
        myTowersInRange = towers;
    }

    public List<BaseEnemy> getEnemiesInRange () {
        return myEnemiesInRange;
    }

    public List<BaseTower> getTowersInRange () {
        return myTowersInRange;
    }

}
