package gameAuthoring.levelData;

import gameAuthoring.mainclasses.Data;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Node;

public class LevelData extends Data {
    
    private static final String LENGTH = "length";
    private static final String MONEY_EARNED = "moneyEarned";
    
    private Map<String, Integer> myNumOfEachTypeOfEnemy;
    private HashMap<String, Integer> myDataMap;
    
    public LevelData(int length, int moneyEarned) {
        myNumOfEachTypeOfEnemy = new HashMap<String, Integer>();
        myDataMap = new HashMap<String, Integer>();
        myDataMap.put(LENGTH, length);
        myDataMap.put(MONEY_EARNED, moneyEarned);
    }
    
    public void addNumberOfEnemiesToLevel(String enemyType, int numberOfEnemies) {
        myNumOfEachTypeOfEnemy.put(enemyType, numberOfEnemies);
    }

    @Override
    public Node getJsonRepresentation () {
        // TODO Auto-generated method stub
        return null;
    }
}
