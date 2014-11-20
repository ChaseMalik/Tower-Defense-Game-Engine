package gamePlayer.mainClasses;

import gamePlayer.guiFeatures.FileLoader;
import gamePlayer.guiItems.headsUpDisplay.GameStats;
import gamePlayer.guiItems.headsUpDisplay.HUD;
import gamePlayer.guiItems.store.Store;
import gamePlayer.guiItemsListeners.GameWorldListener;
import gamePlayer.guiItemsListeners.HUDListener;
import gamePlayer.guiItemsListeners.PlayButtonListener;
import gamePlayer.guiItemsListeners.SpeedButtonListener;
import gamePlayer.guiItemsListeners.StoreListener;
import gamePlayer.guiItemsListeners.VoogaMenuBarListener;
import gamePlayer.mainClasses.dummyGameManager.DummyGameManager;
import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import java.io.File;
import java.util.List;
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
PlayButtonListener, SpeedButtonListener, StoreListener, GameWorldListener
{

    private static final String guiBuilderPropertiesPath = "./src/gamePlayer/properties/GuiBuilderProperties.XML";
    
    private Stage myStage;
    private DummyGameManager myGameManager;
    private Group myRoot;
    
    //handles to GuiItems
    private Store myStore;
    private HUD myHUD;
    
    public GuiManager(Stage stage, DummyGameManager manager) {
        myGameManager = manager;
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
        //myGameManager.saveState("sampleFileName"+Math.random()*1000);l
        System.out.println("Saved game\n");
    }

    @Override
    public void registerStatsBoard(HUD hud) {
        myHUD = hud;
    }
    
    @Override
    public void setGameStats(List<GameStats> stats) {
        myHUD.setGameStats(stats);
    }

    @Override
    public void pause() {
        //myGameManager.pause();
        System.out.println("Pause\n");
    }

    @Override
    public void play() {
        //myGameManager.resume();
        System.out.println("Play\n");
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
    public void normalSpeed () {
        System.out.println("Normal speed\n");
    }

    @Override
    public void fastForward () {
        System.out.println("Fast forward\n");
    }

    @Override
    public void registerStore (Store store) {
        myStore = store;
    }
}
