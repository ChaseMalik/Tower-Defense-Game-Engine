package gamePlayer.mainClasses.dummyGameManager;

import gamePlayer.guiItems.statsBoard.GameStats;
import gamePlayer.mainClasses.GuiManager;
import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;

public class DummyGameManager {
    private GuiManager myGuiManager;
    private List<GameStats> gameStats;
    
    public DummyGameManager(Stage stage) {
        myGuiManager = new GuiManager(stage,this);     
    }
    
    public void run() {
        createGameStats();
        updateGameStats();
    }
    
    public void createGameStats() {
        GameStats score = new GameStats();
        score.setGameStat("Score");
        score.setStatValue(0);
        
        GameStats health = new GameStats();
        health.setGameStat("Health");
        health.setStatValue(100);
        
        gameStats = new ArrayList<GameStats>();
        gameStats.add(score); gameStats.add(health);
        myGuiManager.setGameStats(gameStats);
    }
    
    public void updateGameStats() {
        gameStats.get(0).setStatValue(50);
        gameStats.get(1).setStatValue(50);
    }
}
