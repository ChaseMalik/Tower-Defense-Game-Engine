package gamePlayer.mainClasses.dummyGameManager;

import gamePlayer.guiItems.statsBoard.GameStats;
import gamePlayer.guiItems.statsBoard.StatsBoard;
import java.util.ArrayList;
import java.util.List;

public class DummyGameManager {
    private List<GameStats> gameStats;
    
    public void createGameStats() {
        GameStats score = new GameStats();
        score.setGameStat("Score");
        score.setStatValue(0);
        
        GameStats health = new GameStats();
        health.setGameStat("Health");
        health.setStatValue(100);
        
        gameStats = new ArrayList<GameStats>();
        gameStats.add(score); gameStats.add(health);
        StatsBoard.setGameStats(gameStats);
    }
    
    public void updateGameStats() {
        gameStats.get(0).setStatValue(50);
        gameStats.get(1).setStatValue(50);
    }
}
