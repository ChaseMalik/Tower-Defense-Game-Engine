package gamePlayer.mainClasses;

import gameEngine.MainGameManager;
import gamePlayer.guiFeatures.FileLoader;
import gamePlayer.guiFeatures.TowerPlacer;
import gamePlayer.guiItems.headsUpDisplay.GameStats;
import gamePlayer.guiItems.headsUpDisplay.HUD;
import gamePlayer.guiItemsListeners.GoButtonListener;
import gamePlayer.guiItemsListeners.HUDListener;
import gamePlayer.guiItemsListeners.HealthListener;
import gamePlayer.guiItemsListeners.SelectTowerListener;
import gamePlayer.guiItemsListeners.StoreListener;
import gamePlayer.guiItemsListeners.TowerPlaceListener;
import gamePlayer.guiItemsListeners.VoogaMenuBarListener;
import gamePlayer.mainClasses.dummyGameManager.DummyGameManager;
import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import java.io.File;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.stage.Stage;

/**
 * Class controls all GUI items and MUST implement ALL of the interfaces in the
 * guiItemsListeners package The game engine accesses GUI resources through this
 * class
 * 
 * @author allankiplagat
 *
 */
public class GuiManager implements VoogaMenuBarListener, HUDListener,
StoreListener, GoButtonListener, HealthListener, SelectTowerListener, TowerPlaceListener
{

    private static final String guiBuilderPropertiesPath = "./src/gamePlayer/properties/GuiBuilderProperties.XML";
    private Stage myStage;
    private SelectTowerListener mySelectTowerListener;
    private HUD myStatsBoard;
    private Group myRoot;
    private Group myEngineGroup;

    private MainGameManager myGameManager;

    public GuiManager(Stage stage, DummyGameManager manager) {
        myEngineGroup = new Group();
        //myGameManager = new MainGameManager(this, myEngineGroup);
        myStage = stage;
        GuiConstants.GUI_MANAGER = this;
        myRoot = GuiBuilder.getInstance(guiBuilderPropertiesPath).build(stage);
    }

    @Override
    public void loadGame() {
        File file = FileLoader.getInstance().load(myStage);
        if (file != null) {
            System.out.println(file.getAbsolutePath() + "\n");
        }
    }

    @Override
    public void saveGame() {
        myGameManager.saveState("sampleFileName"+Math.random()*1000);
    }

    @Override
    public void registerStatsBoard(HUD statsBoard) {
        myStatsBoard = statsBoard;
    }

    public void setGameStats(List<GameStats> stats) {
        myStatsBoard.setGameStats(stats);
    }

    @Override
    public void buyItem(String itemID) {

        // TODO : Check to make sure I have enough money
        System.out.println("Buy Item: " + itemID);

        TowerPlacer.getInstance().placeItem(itemID, myRoot);
    }

    public void addItem(String itemID, Dimension2D location) {
        System.out.println("Add Item: " + itemID);
        // For testing purposes...
        mySelectTowerListener.selectTower(itemID);

    }

    @Override
    public void registerTowerListener(SelectTowerListener listener) {
        mySelectTowerListener = listener;
    }

    @Override
    public void pause() {
        myGameManager.pause();
    }

    @Override
    public void play() {
        myGameManager.resume();
    }

    @Override
    public void fastforward() {
        //myGameManager.speedUp();
    }

    @Override
    public void bindHealth(DoubleProperty healthRemaining) {
        //myGameManager.getHealthProperty();
    }

    @Override
    public void changeTheme() {
        File file = FileLoader.getInstance().load(myStage, "StyleSheets", "*.css");
        if (file != null) {
            myStage.getScene().getStylesheets().clear();
            myStage.getScene().getStylesheets().add("file:"+file.getAbsolutePath());
        }
    }

    @Override
    public void selectTower (String towerID) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deselectTower () {
        // TODO Auto-generated method stub

    }

    @Override
    public void placeTower (double x, double y, String towerName) {
        //myGameManager.makeTower(x, y, towerName);
        System.out.println(x + " " + y);
    }
}
