package gamePlayer.mainClasses.testGameManager;

import gamePlayer.guiItems.headsUpDisplay.GameStats;
import gamePlayer.guiItems.store.StoreItem;
import gamePlayer.mainClasses.managers.GuiManager;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Class simulates an engine GameManager for testing purposes
 * @author allankiplagat
 *
 */
public class TestGameManager {
    private GuiManager myGuiManager;
    private List<GameStats> gameStats;
    
    public TestGameManager(Stage stage) {
       // myGuiManager = new GuiManager(stage,this);     
    }
    
    public void run() {
        testLoadingGuiHUD();
        testGuiStore();
    }
    
    private void testGuiStore () {
        Image blackTurret = new Image("gamePlayer/mainClasses/testGameManager/storeItemImages/blackTurret.png");
        Image brownTurret = new Image("gamePlayer/mainClasses/testGameManager/storeItemImages/brownTurret.png");
        Image charcoalTurret = new Image("gamePlayer/mainClasses/testGameManager/storeItemImages/charcoalTurret.png");
        Image greenTurret = new Image("gamePlayer/mainClasses/testGameManager/storeItemImages/greenTurret.png");
        Image purpleTurret = new Image("gamePlayer/mainClasses/testGameManager/storeItemImages/purpleTurret.png");
        Image steelTurret = new Image("gamePlayer/mainClasses/testGameManager/storeItemImages/steelTurret.png");
        
        //properties will be bound in gui such that if engine changes availability, it is automatically reflected in gui
        BooleanProperty blackTurretAvail = new SimpleBooleanProperty(true);
        BooleanProperty brownTurretAvail = new SimpleBooleanProperty(true);
        BooleanProperty charcoalTurretAvail = new SimpleBooleanProperty(true);
        BooleanProperty greenTurretAvail = new SimpleBooleanProperty(true);
        BooleanProperty purpleTurretAvail = new SimpleBooleanProperty(true);
        BooleanProperty steelTurretAvail = new SimpleBooleanProperty(true);
        
        List<StoreItem> itemList = new ArrayList<StoreItem>();
       /* itemList.add(new StoreItem(0,new ImageView(blackTurret),"blackTurret",blackTurretAvail));
        itemList.add(new StoreItem(1,new ImageView(brownTurret),"brownTurret",brownTurretAvail));
        itemList.add(new StoreItem(2,new ImageView(charcoalTurret),"charcoalTurret",charcoalTurretAvail));
        itemList.add(new StoreItem(3,new ImageView(greenTurret),"greenTurret",greenTurretAvail));     
        itemList.add(new StoreItem(4,new ImageView(purpleTurret),"purpleTurret",purpleTurretAvail));
        itemList.add(new StoreItem(5,new ImageView(steelTurret),"steelTurret",steelTurretAvail));   */
   
       // myGuiManager.fillStore(itemList);
        
        //change availabilities and refresh display
        greenTurretAvail.set(false);
        purpleTurretAvail.set(false);
        steelTurretAvail.set(false);
        myGuiManager.refreshStore();
    }

    private void testLoadingGuiHUD() {
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
