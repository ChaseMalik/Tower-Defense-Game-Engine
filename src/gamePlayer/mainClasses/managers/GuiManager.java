package gamePlayer.mainClasses.managers;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gameEngine.NullTowerInfoObject;
import gameEngine.SingleThreadedEngineManager;
import gameEngine.TowerInfoObject;
import gamePlayer.guiFeatures.FileLoader;
import gamePlayer.guiFeatures.TowerPlacer;
import gamePlayer.guiItems.gameWorld.GameWorld;
import gamePlayer.guiItems.headsUpDisplay.GameStat;
import gamePlayer.guiItems.headsUpDisplay.HUD;
import gamePlayer.guiItems.messageDisplay.MessageDisplay;
import gamePlayer.guiItems.store.Store;
import gamePlayer.guiItems.store.StoreItem;
import gamePlayer.guiItems.towerUpgrade.TowerIndicator;
import gamePlayer.guiItems.towerUpgrade.TowerUpgradePanel;
import gamePlayer.guiItemsListeners.GameItemListener;
import gamePlayer.guiItemsListeners.GameWorldListener;
import gamePlayer.guiItemsListeners.HUDListener;
import gamePlayer.guiItemsListeners.MessageDisplayListener;
import gamePlayer.guiItemsListeners.PlayButtonListener;
import gamePlayer.guiItemsListeners.SpeedButtonListener;
import gamePlayer.guiItemsListeners.SpeedSliderListener;
import gamePlayer.guiItemsListeners.StoreListener;
import gamePlayer.guiItemsListeners.UpgradeListener;
import gamePlayer.guiItemsListeners.VoogaMenuBarListener;
import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utilities.JavaFXutilities.CenteredImageView;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
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
		GameWorldListener, GameItemListener, UpgradeListener, MessageDisplayListener, SpeedSliderListener {

	private static String guiBuilderPropertiesPath = "./src/gamePlayer/properties/GuiBuilderProperties.XML";
	public static final String NO_UPGRADE = "No update available";
	public static final String NO_GOLD = "Not enough gold available";

	private Stage myStage;
	private SingleThreadedEngineManager myEngineManager;
	private Group myRoot;
	private TowerIndicator activeIndicator;
	private ImageView activeTower;
	private boolean gameRunning;

	private Store myStore;
	private HUD myHUD;
	private GameWorld myGameWorld;
	private TowerUpgradePanel myUpgradePanel;
	private MessageDisplay myMessageDisplay;
	private Map<String, TowerInfoObject> towerMap;

	public GuiManager(Stage stage) {
		myStage = stage;
		GuiConstants.GUI_MANAGER = this;	
		gameRunning = false;
	}
	
	public void init() {
		myRoot = GuiBuilder.getInstance().build(myStage, guiBuilderPropertiesPath);
	}
	
	private void startGame(String directoryPath){
	    myEngineManager = new SingleThreadedEngineManager(myGameWorld.getMap());
		myEngineManager.initializeGame(directoryPath);
		addBackground(directoryPath);
		makeTowerMap();
		testHUD();
		//myRoot.getChildren().add(engineGroup);
		fillStore(myEngineManager.getAllTowerTypeInformation());
		//myGameWorld.getMap().getStyleClass().add("GameWorld");
		//System.out.println(BuildingPane.DRAW_SCREEN_WIDTH + " " + AuthorController.SCREEN_HEIGHT);
		gameRunning = true;
	}
	
	private void addBackground(String directory){
		File parent = new File(directory+="/background/");
		File background = parent.listFiles()[0];
		myGameWorld.setBackground(background.getAbsolutePath());
	}
	
	private void makeTowerMap(){
	    towerMap = new HashMap<String, TowerInfoObject>();
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
	public void registerGameWorld(GameWorld world){
		myGameWorld = world;
	}
	
	@Override
	public void registerUpgradePanel(TowerUpgradePanel upgradePanel){
		myUpgradePanel = upgradePanel;
	}

	@Override
	public void setGameStats(List<GameStat> stats) {
		myHUD.setGameStats(stats);
	}

	@Override
	public void pause() {
		if (!gameRunning) return;
		myEngineManager.pause();
	}

	@Override
	public void play() {
		if (!gameRunning) return;
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
		if (!gameRunning) return;
		//myEngineManager.changeRunSpeed(1.0);
		play();
	}

	@Override
	public void fastForward() {
		if (!gameRunning) return;
		//myEngineManager.changeRunSpeed(3.0);
		play();
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
	public void upgradeTower(ImageView imageView, String upgradeName) {
		if (!gameRunning) return;
		if (upgradeName.equals(NO_UPGRADE) && !myEngineManager.checkGold(towerMap.get(upgradeName))){ 
			displayMessage(upgradeName, true);
			return;
		}
		DoubleProperty gold=myEngineManager.myGold();
		myEngineManager.setMyGold(gold.get()-towerMap.get(upgradeName).getBuyCost());
		ImageView newTower = myEngineManager.upgrade(imageView, upgradeName);
		//if (newTower == null) displayMessage(NO_GOLD, true);
		newTower.setOnMouseClicked(event -> selectTower(upgradeName, newTower));
		selectTower(upgradeName, newTower);
	}
	
	private void testHUD() {
		List<GameStat> gameStats;
        GameStat level = new GameStat();
        level.setGameStat("Level");
        level.setStatValue(1);
        
        GameStat gold = new GameStat();
        gold.setGameStat("Gold");
        gold.statValueProperty().bindBidirectional(myEngineManager.myGold());
        
        GameStat health = new GameStat();
        health.setGameStat("Health");
        health.setStatValue(100);
        
        gameStats = new ArrayList<GameStat>();
        gameStats.add(level); gameStats.add(gold); gameStats.add(health);
        this.setGameStats(gameStats);
        
        //update game stats
       // gameStats.get(1).setStatValue(50);
      //  gameStats.get(2).setStatValue(50);
    }
	
	public void makeTower(String towerName, double x, double y) {
		if (!gameRunning) return;
                if (!myEngineManager.checkGold(towerMap.get(towerName))){ 
                    displayMessage(towerName, true);
                    return;
            }
            DoubleProperty gold=myEngineManager.myGold();
            myEngineManager.setMyGold(gold.get()-towerMap.get(towerName).getBuyCost());		
		ImageView towerImageView = myEngineManager.addTower(towerName, x, y);
//		if(towerImageView == null) {
//			displayMessage(NO_GOLD, true);
//		    return;
//		}
		towerImageView.setOnMouseClicked(event -> selectTower(towerName, towerImageView));
	}
	
	private void selectTower(String towerName, ImageView tower){
		CenteredImageView centered = (CenteredImageView)tower;
		double radius = towerMap.get(towerName).getRange();
		deselectTower(activeIndicator, activeTower, myEngineManager.getTowerName(activeTower));
		activeIndicator = new TowerIndicator(centered.getXCenter(), centered.getYCenter(), radius);
		activeTower = tower;
		myUpgradePanel.setCurrentTower(towerMap.get(towerName), tower, activeIndicator);
		myGameWorld.getMap().getChildren().add(activeIndicator);
		tower.setOnMouseClicked(event -> deselectTower(activeIndicator, tower, towerName));
		tower.getParent().toFront();
	}
	
	private void deselectTower(TowerIndicator indicator, ImageView tower, String towerName) {
		myGameWorld.getMap().getChildren().remove(indicator);
		myUpgradePanel.setCurrentTower(new NullTowerInfoObject(), null, null);
		if (tower != null) tower.setOnMouseClicked(event -> selectTower(towerName, tower));
	}
	
	@Override
	public void placeTower(String towerName) {
		TowerPlacer.getInstance().placeItem(towerName, myGameWorld.getMap(), towerMap.get(towerName).getRange());
	}

	@Override
	public void registerMessageDisplayListener(MessageDisplay display) {
		myMessageDisplay = display;
	}

	@Override
	public void displayMessage(String message, boolean error) {
		myMessageDisplay.showMessage(message, error);
	}

	@Override
	public void clearMessageDisplay() {
		myMessageDisplay.clear();		
	}

	@Override
	public void selectItem(int itemID) {
		
	}

	@Override
	public void sellTower(ImageView myTowerImageView, TowerIndicator indicator) {
		myEngineManager.sellTower(myTowerImageView);
		myGameWorld.getMap().getChildren().remove(indicator);
	}

	@Override
	public void updateSpeed() {
		// TODO Auto-generated method stub
		
	}

	/*
	 * For Tower Placing
	 */
	public boolean validPlacement(double x, double y) {
		return myEngineManager.validateTower(x, y);
	}
}
