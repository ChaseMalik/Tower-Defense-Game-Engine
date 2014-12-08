package gameAuthoring.scenes.levelBuilding;

import gameEngine.actors.BaseEnemy;

/**
 * A data object essentially representing a map key-value pair that makes
 * writing to GSON possible. This avoids having a map with a map inside of it.
 * @author Austin Kyker
 *
 */
public class EnemyCountPair {
    private int myNumEnemies;
    private BaseEnemy myEnemy;

    public EnemyCountPair (int num, BaseEnemy enemy) {
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
