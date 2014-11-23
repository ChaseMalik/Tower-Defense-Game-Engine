package gameEngine.actors;

import java.util.List;

public class InfoObject {

    private List<BaseActor> myEnemiesInRange;
    private List<BaseActor> myTowersInRange;
    private List<BaseActor> myProjectilesInRange;
    public InfoObject(List<BaseActor> enemies, List<BaseActor> towers, List<BaseActor> projectiles){
        myEnemiesInRange = enemies;
        myTowersInRange = towers;
        myProjectilesInRange=projectiles;
    }

    public List<BaseActor> getEnemiesInRange () {
        return myEnemiesInRange;
    }

    public List<BaseActor> getTowersInRange () {
        return myTowersInRange;
    }
    public List<BaseActor> getProjectilesInRange(){
        return myProjectilesInRange;
    }

}
