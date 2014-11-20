package gamePlayer.mainClasses.dummyGameManager;

import gamePlayer.guiItems.headsUpDisplayB.GameStats;
import gamePlayer.mainClasses.GuiManager;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;

/**
 * Class simulates an engine GameManager for testing purposes
 * @author allankiplagat
 *
 */
public class DummyGameManager {
    private GuiManager myGuiManager;
    private List<GameStats> gameStats;
    
    public DummyGameManager(Stage stage) {
        myGuiManager = new GuiManager(stage,this);     
    }
    
    public void run() {
        testGameStats();
    }
    
    public void testGameStats() {
        GameStats level = new GameStats();
        level.setGameStat("Level");
        level.setStatValue(1);
        
        GameStats score = new GameStats();
        score.setGameStat("Score");
        score.setStatValue(0);
        
        GameStats health = new GameStats();
        health.setGameStat("Health");
        health.setStatValue(100);
        
        gameStats = new ArrayList<GameStats>();
        gameStats.add(level); gameStats.add(score); gameStats.add(health);
        myGuiManager.setGameStats(gameStats);
        
        //update game stats
        gameStats.get(1).setStatValue(50);
        gameStats.get(2).setStatValue(50);
    }
}
