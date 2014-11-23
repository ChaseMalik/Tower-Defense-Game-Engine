package gamePlayer.mainClasses;

import gameEngine.NullTowerInfoObject;
import gameEngine.SingleThreadedEngineManager;
import gameEngine.TowerInfoObject;
import gamePlayer.guiFeatures.FileLoader;
import gamePlayer.guiItems.headsUpDisplay.GameStats;
import gamePlayer.guiItems.headsUpDisplay.HUD;
import gamePlayer.guiItems.store.Store;
import gamePlayer.guiItems.store.StoreItem;
import gamePlayer.guiItemsListeners.GameItemListener;
import gamePlayer.guiItemsListeners.GameWorldListener;
import gamePlayer.guiItemsListeners.HUDListener;
import gamePlayer.guiItemsListeners.PlayButtonListener;
import gamePlayer.guiItemsListeners.SpeedButtonListener;
import gamePlayer.guiItemsListeners.StoreListener;
import gamePlayer.guiItemsListeners.VoogaMenuBarListener;
import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.testGameManager.TestGameManager;
import gamePlayer.towerUpgrade.UpgradeListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Class controls all GUI items and MUST implement ALL of the interfaces in the
 * guiItemsListeners package The game engine accesses GUI resources through this
 * class
 * 
 * @author allankiplagat, Greg Lyons
 *
 */
public class GuiManager implements VoogaMenuBarListener, HUDListener,
		PlayButtonListener, SpeedButtonListener, StoreListener,
		GameWorldListener, GameItemListener, UpgradeListener {

	private static final String guiBuilderPropertiesPath = "./src/gamePlayer/properties/GuiBuilderProperties.XML";

	private Stage myStage;
	private SingleThreadedEngineManager myEngineManager;
	private Group myRoot;

	// handles to GuiItems
	private Store myStore;
	private HUD myHUD;
	private Map<String, TowerInfoObject> towerMap;

	public GuiManager(Stage stage) {
		myStage = stage;
		GuiConstants.GUI_MANAGER = this;
		Group engineGroup = new Group();
		myEngineManager = new SingleThreadedEngineManager(engineGroup);
		myRoot = GuiBuilder.getInstance(guiBuilderPropertiesPath).build(stage);
	}
	
	private void startGame(String directoryPath){
		myEngineManager.initializeGame(directoryPath);
		myEngineManager.getAllTowerTypeInformation();
		makeMap();
	}
	
	private void makeMap(){
		for (TowerInfoObject info: myEngineManager.getAllTowerTypeInformation()){
			towerMap.put(info.getName(), info);
			TowerInfoObject next = info.getMyUpgrade();
			while(!(next instanceof NullTowerInfoObject)){
				towerMap.put(next.getName(), next);
				next = next.getMyUpgrade();
			}
		}
	}


	@Override
	public void loadGame() {
		File file = FileLoader.getInstance().load(myStage);
		if (file != null) {
			System.out.println(file.getAbsolutePath() + "\n");
			startGame(file.getAbsolutePath());
		}
	}

	@Override
	public void saveGame() {
		//myEngineManager.saveState("sampleFileName"+Math.random()*1000);l
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
		myEngineManager.pause();
	}

	@Override
	public void play() {
		myEngineManager.resume();
	}

	@Override
	public void changeTheme() {
		File file = FileLoader.getInstance().load(myStage, "StyleSheets",
				"*.css");
		if (file != null) {
			myStage.getScene().getStylesheets().clear();
			myStage.getScene().getStylesheets()
					.add("file:" + file.getAbsolutePath());
		}
	}

	@Override
	public void normalSpeed() {
		myEngineManager.changeRunSpeed(1.0);
	}

	@Override
	public void fastForward() {
		myEngineManager.changeRunSpeed(3.0);
	}

	@Override
	public void registerStore(Store store) {
		myStore = store;
	}

	@Override
	public void fillStore(Collection<TowerInfoObject> towersAvailable) {
		List<StoreItem> storeItems = new ArrayList<StoreItem>();
		for (TowerInfoObject info: towersAvailable) {
			StoreItem newItem = new StoreItem(info.getName(), info.getImageLocation(), new SimpleBooleanProperty(true));
			storeItems.add(newItem);
		}
		myStore.fillStore(storeItems);
	}

	@Override
	public void refreshStore() {
		myStore.refreshStore();
	}

	@Override
	public void selectItem(int itemID) {
		
	}

	@Override
	public void upgradeTower(Class newTower, double x, double y) {
		
	}
}
