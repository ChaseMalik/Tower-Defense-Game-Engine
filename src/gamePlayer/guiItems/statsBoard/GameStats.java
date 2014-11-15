package gamePlayer.guiItems.statsBoard;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameStats {
    private StringProperty gameStat;
    public void setGameStat(String value) {
        gameStatProperty().set(value); 
    }

    public String getGameStat() { 
        return gameStatProperty().get(); 
    }

    public StringProperty gameStatProperty() { 
        if (gameStat == null) gameStat = new SimpleStringProperty(this, "gameStat");
        return gameStat; 
    }

    private IntegerProperty statValue;
    public void setStatValue(Integer value) { 
        statValueProperty().set(value); 
    }

    public int getStatValue() { 
        return statValueProperty().get(); 
    }
    
    public IntegerProperty statValueProperty() { 
        if (statValue == null) statValue = new SimpleIntegerProperty(this, "statValue");
        return statValue; 
    }
}
