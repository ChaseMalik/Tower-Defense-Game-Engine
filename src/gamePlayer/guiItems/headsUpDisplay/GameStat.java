package gamePlayer.guiItems.headsUpDisplay;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This engine class encapsulates the overall game statistics such as score and health
 * that are going to be observable on the GUI
 * @author allankiplagat
 *
 */
public class GameStat {
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

    private DoubleProperty statValue;
    public void setStatValue(double value) { 
        statValueProperty().set(value); 
    }

    public double getStatValue() { 
        return statValueProperty().get(); 
    }
    
    public DoubleProperty statValueProperty() { 
        if (statValue == null) statValue = new SimpleDoubleProperty(this, "statValue");
        return statValue; 
    }
}
