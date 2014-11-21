package gameEngine.actors;

import java.util.List;

public class InfoObject {

    private List<BaseActor> myEnemiesInRange;
    private List<BaseActor> myTowersInRange;
    
    public InfoObject(List<BaseActor> enemies, List<BaseActor> towers){
        myEnemiesInRange = enemies;
        myTowersInRange = towers;
    }

    public List<BaseActor> getEnemiesInRange () {
        return myEnemiesInRange;
    }

    public List<BaseActor> getTowersInRange () {
        return myTowersInRange;
    }

}
