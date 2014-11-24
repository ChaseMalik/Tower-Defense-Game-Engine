package gameAuthoring.scenes.levelBuilding;

import gameEngine.actors.BaseEnemy;

public class EnemyCountPair {
    private int myNumEnemies;
    private BaseEnemy myEnemy;
    
    public EnemyCountPair(int num, BaseEnemy enemy) {
        myNumEnemies = num;
        myEnemy = enemy;
    }

    public int getMyNumEnemies () {
        return myNumEnemies;
    }

    public BaseEnemy getMyEnemy () {
        return myEnemy;
    }

    public void setMyCount (int num) {
        myNumEnemies = num;      
    }
}
