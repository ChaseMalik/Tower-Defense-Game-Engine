package gamePlayer.mainClasses.managers;

import gameEngine.CoOpManager;
import gameEngine.MainEngineManager;
import gameEngine.Data.NullTowerInfoObject;
import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiFeatures.FileLoader;
import gamePlayer.guiFeatures.LMController;
import gamePlayer.guiFeatures.TowerPlacer;
import gamePlayer.guiFeatures.WinStatusProperty;
import gamePlayer.guiFeatures.earthquake.EarthquakeController;
import gamePlayer.guiFeatures.earthquake.LMEarthquakeStrategy;
import gamePlayer.guiFeatures.earthquake.MouseEarthquakeStrategy;
import gamePlayer.guiItems.controlDockButtons.sliders.SpeedSlider;
import gamePlayer.guiItems.gameWorld.GameWorld;
import gamePlayer.guiItems.headsUpDisplay.GameStat;
import gamePlayer.guiItems.headsUpDisplay.HUD;
import gamePlayer.guiItems.messageDisplay.MessageDisplay;
import gamePlayer.guiItems.store.Store;
import gamePlayer.guiItems.store.StoreItem;
import gamePlayer.guiItems.towerUpgrade.TowerIndicator;
import gamePlayer.guiItems.towerUpgrade.TowerUpgradePanel;
import gamePlayer.guiItemsListeners.EarthquakeListener;
import gamePlayer.guiItemsListeners.GameWorldListener;
import gamePlayer.guiItemsListeners.HUDListener;
import gamePlayer.guiItemsListeners.MessageDisplayListener;
import gamePlayer.guiItemsListeners.PlayButtonListener;
import gamePlayer.guiItemsListeners.SpecialButtonListener;
import gamePlayer.guiItemsListeners.SpeedButtonListener;
import gamePlayer.guiItemsListeners.SpeedSliderListener;
import gamePlayer.guiItemsListeners.StoreListener;
import gamePlayer.guiItemsListeners.UpgradeListener;
import gamePlayer.guiItemsListeners.VoogaMenuBarListener;
import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.welcomeScreen.GameStartManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utilities.JavaFXutilities.imageView.CenteredImageView;

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
		GameWorldListener, UpgradeListener, MessageDisplayListener,
		SpeedSliderListener, SpecialButtonListener, EarthquakeListener {

	private static final String guiBuilderPropertiesPath = "./src/gamePlayer/properties/GuiBuilderProperties.XML";
	
	public static final String LOSS = "GAME OVER";
	public static final String WIN = "Congratulations, you won";
	public static final String NO_UPGRADE = "No update available";
	public static final String NO_GOLD = "Not enough gold available";
	public static final String ESCAPE_TEXT = "Press ESC to escape from tower placement";
	public static final String SCORE = "Your score: ";
	public static final String PLAY_AGAIN = "Click anywhere on the map to play again";

	private Stage myStage;
	private MainEngineManager myEngineManager;
	private CoOpManager myCoOpManager;

	private TowerIndicator activeIndicator;
	private ImageView activeTower;
	private boolean interactionAllowed;
	private EarthquakeController earthquakeController;

	private Store myStore;
	private HUD myHUD;
	private GameWorld myGameWorld;
	private TowerUpgradePanel myUpgradePanel;
	private SpeedSlider mySpeedSlider;
	private MessageDisplay myMessageDisplay;
	private Map<String, TowerInfoObject> towerMap;
	private List<GameStat> gameStats;
	private double myScore;
	private boolean isCoOp;
	private String myDirectory;
	
	private GameStat level;
	private GameStat health;
	private GameStat gold;
	private DoubleProperty endgame;
	
	public GuiManager(Stage stage) {
		myStage = stage;
		GuiConstants.GUI_MANAGER = this;
	}

	public void init() {
		GuiConstants.DYNAMIC_SIZING = true;
		GuiBuilder.getInstance().build(myStage, guiBuilderPropertiesPath);
		if (LMController.getInstance().deviceIsConnected()) {
			initWithLM();
		}
	}

	private void startGame(String directoryPath) {
		myEngineManager = new MainEngineManager(myGameWorld.getMap());
		myEngineManager.initializeGame(directoryPath);
		addBackground(directoryPath);
		makeTowerMap();
		setUpHUD();
		fillStore(myEngineManager.getAllTowerTypeInformation());
		interactionAllowed = true;
	}

	@Override
	public void loadGame() {
		File file = FileLoader.getInstance().loadDirectory(myStage);
		if (file != null) {
			startGame(file.getAbsolutePath());
		}
	}

	@Override
	public void loadState() {
		File file = FileLoader.getInstance().load(myStage, "Json", "*.json");
		if (file != null) {
			myEngineManager
					.loadState(file.getAbsolutePath().replace("\\", "/"));
		}
	}

	public void startSinglePlayerGame(String directoryPath) {
		myEngineManager = new MainEngineManager(myGameWorld.getMap());
		myEngineManager.initializeGame(directoryPath);
		initializeNewGameElements(directoryPath);
		myDirectory = directoryPath;
		interactionAllowed = true;
	}

	@Override
	public void play() {
		if (!interactionAllowed || isCoOp)
			return;
		myEngineManager.resume();
	}

	public void joinMultiPlayerGame() {
		myCoOpManager = new CoOpManager();
		myEngineManager = myCoOpManager;
		if (myCoOpManager.joinGame()) {
			startMultiPlayerGame();
		}
	}

	public void prepareMultiPlayerGame(String directoryPath) {
		myCoOpManager = new CoOpManager();
		myEngineManager = myCoOpManager;
		myCoOpManager.startNewGame(directoryPath);
	}

	public boolean multiPlayerGameIsReady() {
		return myCoOpManager.isReady();
	}

	public void startMultiPlayerGame() {
		isCoOp = true;
		GuiConstants.GUI_MANAGER.init();
		String dir = myCoOpManager.initializeGame(myGameWorld.getMap());
		initializeNewGameElements(dir);

		GameStat time = new GameStat();
		time.setGameStat("Time");
		time.statValueProperty().bindBidirectional(myCoOpManager.getTimer());
		time.statValueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> o,
					Number oldValue, Number newValue) {
				if ((double) newValue <= 0.0) {
					interactionAllowed = false;
					myStore.freeze();
				} else {
					interactionAllowed = true;
					myStore.unfreeze();
				}
			}
		});
		gameStats.add(time);
		this.setGameStats(gameStats);
	}

	private void initializeNewGameElements(String directoryPath) {
		addBackground(directoryPath);
		makeTowerMap();
		setUpHUD();
		fillStore(myEngineManager.getAllTowerTypeInformation());
		
		endgame = new WinStatusProperty();
		endgame.bindBidirectional(myEngineManager.getWinStatus());
		endgame.addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
				double status = (double)newValue;
				if (status < 0.0) endGame(LOSS);
			    if (status > 0.0) endGame(WIN);
			}
		});
	}
		
	private void addBackground(String directory) {
		File parent = new File(directory += "/background/");
		File background = parent.listFiles()[0];
		myGameWorld.setBackground(background.getAbsolutePath());
	}

	@Override
	public void saveGame() {
		File file = FileLoader.getInstance().save(myStage);
		if (file != null) {
			myEngineManager.saveState(file.getParent().replace("\\", "/"),
					file.getName());
		}
	}

	@Override
	public void registerStatsBoard(HUD hud) {
		myHUD = hud;
	}

	@Override
	public void registerGameWorld(GameWorld world) {
		myGameWorld = world;
	}

	@Override
	public void registerUpgradePanel(TowerUpgradePanel upgradePanel) {
		myUpgradePanel = upgradePanel;
	}

	@Override
	public void setGameStats(List<GameStat> stats) {
		myHUD.setGameStats(stats);
	}

	@Override
	public void pause() {
		if (!interactionAllowed)
			return;
		myEngineManager.pause();
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
		if (!interactionAllowed)
			return;
		play();
	}

	@Override
	public void fastForward() {
		if (!interactionAllowed)
			return;
		play();
	}

	@Override
	public void registerStore(Store store) {
		myStore = store;
	}

	@Override
	public void fillStore(Collection<TowerInfoObject> towersAvailable) {
		List<StoreItem> storeItems = new ArrayList<StoreItem>();
		for (TowerInfoObject info : towersAvailable) {
			StoreItem newItem = new StoreItem(info.getName(),
					info.getImageLocation(), info.getBuyCost(),
					new SimpleBooleanProperty(true));
			storeItems.add(newItem);
		}
		myStore.fillStore(storeItems);
	}

	@Override
	public void refreshStore() {
		myStore.refreshStore();
	}

	public boolean upgradeTower(ImageView imageView, String upgradeName) {
		if (!interactionAllowed)
			return false;
		if (upgradeName.equals(NO_UPGRADE)) {
			displayMessage(NO_UPGRADE, true);
			return false;
		}
		if (!myEngineManager.checkGold(towerMap.get(upgradeName))) {
			displayMessage(NO_GOLD, true);
			return false;
		}
		DoubleProperty gold = myEngineManager.getGoldProperty();
		myEngineManager.setMyGold(gold.get()
				- towerMap.get(upgradeName).getBuyCost());
		ImageView newTower = myEngineManager.upgrade(imageView, upgradeName);
		// if (newTower == null) displayMessage(NO_GOLD, true);
		newTower.setOnMouseClicked(event -> selectTower(upgradeName, newTower));
		selectTower(upgradeName, newTower);
		return true;
	}

	private void setUpHUD() {
		gameStats = new ArrayList<GameStat>();
		level = new GameStat();
		level.setGameStat("Level");
		level.statValueProperty().bindBidirectional(myEngineManager.getCurrentLevelProperty());

		gold = new GameStat();
		gold.setGameStat("Gold");
		gold.statValueProperty().bindBidirectional(
				myEngineManager.getGoldProperty());

		GameStat health = new GameStat();
		health.setGameStat("Health");
		health.statValueProperty().bindBidirectional(
				myEngineManager.getHealthProperty());
		gold.statValueProperty().bindBidirectional(myEngineManager.getGoldProperty());
		
		health = new GameStat();
		health.setGameStat("Health");
		health.statValueProperty().bindBidirectional(myEngineManager.getHealthProperty());
		/*health.statValueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
				if ((double)newValue <= 0.0)
					endGame(LOSS);
			}
		});*/

		gameStats = new ArrayList<GameStat>();
		// gameStats.add(level);
		gameStats.add(gold);
		gameStats.add(health);
		this.setGameStats(gameStats);

	}
	
	private void endGame(String endCondition){
		displayMessage(endCondition + "\n" + PLAY_AGAIN, true);
		myGameWorld.getMap().setOnMouseClicked(event -> new GameStartManager(myStage));
	}

	public void makeTower(String towerName, double x, double y) {
		if (!interactionAllowed)
			return;
		displayMessage(MessageDisplay.DEFAULT, false);
		if (!myEngineManager.checkGold(towerMap.get(towerName))) {
			displayMessage(NO_GOLD, true);
			return;
		}
		DoubleProperty gold = myEngineManager.getGoldProperty();
		myEngineManager.setMyGold(gold.get()
				- towerMap.get(towerName).getBuyCost());
		ImageView towerImageView = myEngineManager.addTower(towerName, x, y);
		if (towerImageView == null) {
			displayMessage(NO_GOLD, true);
			return;
		}
		towerImageView.setOnMouseClicked(event -> selectTower(towerName,
				towerImageView));
	}

	private void selectTower(String towerName, ImageView tower) {
		if (!interactionAllowed)
			return;
		CenteredImageView centered = (CenteredImageView) tower;
		double radius = towerMap.get(towerName).getRange();
		deselectTower(activeIndicator, activeTower,
				myEngineManager.getTowerName(activeTower));
		activeIndicator = new TowerIndicator(centered.getXCenter(),
				centered.getYCenter(), radius);
		activeTower = tower;
		myUpgradePanel.setCurrentTower(towerMap.get(towerName), tower,
				activeIndicator);
		myGameWorld.getMap().getChildren().add(activeIndicator);
		tower.setOnMouseClicked(event -> deselectTower(activeIndicator, tower,
				towerName));
		tower.getParent().toFront();
	}

	private void deselectTower(TowerIndicator indicator, ImageView tower,
			String towerName) {
		myGameWorld.getMap().getChildren().remove(indicator);
		// myUpgradePanel.setCurrentTower(new NullTowerInfoObject(), null,
		// null);
		myUpgradePanel.deselectTower();
		if (tower != null)
			tower.setOnMouseClicked(event -> selectTower(towerName, tower));
	}

	private boolean checkGold(String towerName) {
		double cost = towerMap.get(towerName).getBuyCost();
		return cost <= gold.getStatValue();
	}

	@Override
	public void placeTower(String towerName) {
		if (!checkGold(towerName)) {
			displayMessage(NO_GOLD, true);
			return;
		}
		TowerPlacer.getInstance().placeItem(towerName, myGameWorld.getMap(),
				towerMap.get(towerName).getRange());
		displayMessage(ESCAPE_TEXT, false);
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
	public void sellTower(ImageView myTowerImageView, TowerIndicator indicator) {
		if (!interactionAllowed)
			return;
		myEngineManager.sellTower(myTowerImageView);
		myGameWorld.getMap().getChildren().remove(indicator);
	}

	/*
	 * For Tower Placing
	 */
	public boolean validPlacement(double x, double y) {
		return myEngineManager.validateTower(x, y);
	}

	@Override
	public void changeSpeed(double d) {
		myEngineManager.changeRunSpeed(d);
	}

	private void makeTowerMap() {
		towerMap = new HashMap<String, TowerInfoObject>();
		for (TowerInfoObject info : myEngineManager
				.getAllTowerTypeInformation()) {
			towerMap.put(info.getName(), info);
			TowerInfoObject next = info.getMyUpgrade();
			while (!(next instanceof NullTowerInfoObject)) {
				towerMap.put(next.getName(), next);
				next = next.getMyUpgrade();
			}
		}
	}

	private void initWithLM() {
		LMController controller = LMController.getInstance();
		controller.onCircleCW(event -> incrementSpeed());
		controller.onCircleCCW(event -> mySpeedSlider.decrementSpeed());
	}

	@Override
	public void registerSpeedSlider(SpeedSlider slider) {
		mySpeedSlider = slider;
	}

	@Override
	public void escapePlace() {

		myGameWorld.getMap().setOnMouseMoved(null);
		myGameWorld.getMap().setOnMouseReleased(null);
		myGameWorld.getMap().getChildren()
				.remove(myGameWorld.getMap().getChildren().size() - 1); // remove
																		// range
																		// circle
																		// (last
																		// thing
																		// added
																		// to
																		// children)
		displayMessage(MessageDisplay.DEFAULT, false);

	}

	public void switchGame() {

	}

	public void replayGame() {
		init();
		if (isCoOp)
			startMultiPlayerGame();
		else
			startSinglePlayerGame(myDirectory);
	}

	@Override
	public double specialSelected() {
		displayMessage("Earthquake! Wave your hands as fast as you can!", false);
		// TODO : Create Vibrator
		double maxMag = 5;
		double length = 5;
		if (earthquakeController == null) {
			if (LMController.getInstance().deviceIsConnected()) {
				earthquakeController = new EarthquakeController(maxMag,
						new LMEarthquakeStrategy(), (EarthquakeListener) this);
			} else {
				earthquakeController = new EarthquakeController(maxMag,
						new MouseEarthquakeStrategy(),
						(EarthquakeListener) this);
			}
			
		}
		earthquakeController.start(length);

		return length;
	}

	@Override
	public void vibrate(double magnitude) {
		myEngineManager.setEarthquakeMagnitude(magnitude);
		Pane gameworld = myGameWorld.getMap();
		if (magnitude == -1) {
			gameworld.setTranslateX(0);
			gameworld.setTranslateY(0);
			return;
		}
		Random rand = new Random();
		double factor = 2 * rand.nextDouble() - 1;
		gameworld.setTranslateX(factor * 5 * magnitude);
		factor = 2 * rand.nextDouble() - 1;
		gameworld.setTranslateY(factor * 5 * magnitude);
	}

	public void addEventFilter(EventType<MouseEvent> eventType,
			EventHandler<MouseEvent> handler) {
		myStage.addEventFilter(eventType, handler);
	}

	public void removeEventFilter(EventType<MouseEvent> eventType,
			EventHandler<MouseEvent> handler) {
		myStage.removeEventFilter(eventType, handler);
	}

	private void incrementSpeed() {
		if (myEngineManager.isRunning())
			play();
		else
			mySpeedSlider.incrementSpeed();
	}

	public void selectGame() {
		new GameStartManager(myStage);
	}
}
