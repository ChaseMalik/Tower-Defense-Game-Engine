package gameEngine;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.actorBuildingScenes.TowerUpgradeGroup;
import gameAuthoring.scenes.levelBuilding.EnemyCountPair;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gameAuthoring.scenes.pathBuilding.buildingPanes.towerRegions.Tile;
import gameEngine.Data.NullTowerInfoObject;
import gameEngine.Data.RangeRestrictedCollection;
import gameEngine.Data.TowerInfoObject;
import gameEngine.Data.TowerTileGrid;
import gameEngine.ManagerInterface.InformationInterface;
import gameEngine.ManagerInterface.UpdateInterface;
import gameEngine.ManagerInterface.UpdateObject;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseProjectile;
import gameEngine.actors.BaseTower;
import gameEngine.levels.BaseLevel;
import gamePlayer.guiFeatures.WinStatusProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import utilities.GSON.GSONFileReader;
import utilities.GSON.GSONFileWriter;
import utilities.GSON.objectWrappers.DataWrapper;
import utilities.GSON.objectWrappers.GameStateWrapper;
import utilities.GSON.objectWrappers.GeneralSettingsWrapper;
import utilities.JavaFXutilities.imageView.CenteredImageView;
import utilities.errorPopup.ErrorPopup;


public class MainEngineManager implements Observer, UpdateInterface, InformationInterface {

    private static final String UNKNOWN_SAVE_STATE_FILE_MESSAGE = "Unknown problem loading save file";
	private static final String INCORRECT_SAVE_STATE_MESSAGE = "Save state does not correspond to this game";
	private static final String JSON_EXTENSION = ".json";
	private static final String DIRECTORY_SPLIT = "/";
	private static final int FPS = 30;
    private static final double FRAME_DURATION = 1000.0 / 30;

    private double myLastUpdateTime;
    private Timeline myTimeline;
    private double myUpdateRate;
    private AtomicBoolean myReadyToPlay;
    protected RangeRestrictedCollection<BaseTower> myTowerGroup;
    private RangeRestrictedCollection<BaseEnemy> myEnemyGroup;
    private RangeRestrictedCollection<BaseProjectile> myProjectileGroup;
    private double duration;
    private List<BaseLevel> myLevels;
    private int myCurrentLevelIndex;
    private GridPane myValidRegions;
    private SimpleDoubleProperty myGold;
    private Map<String, BaseTower> myPrototypeTowerMap;
    private double myIntervalBetweenEnemies;
    private Queue<BaseEnemy> myEnemiesToAdd;
    private SimpleDoubleProperty myHealth;
    private SimpleDoubleProperty myEarthquakeMagnitude;
    private Map<Node, BaseTower> myNodeToTower;
    private Collection<TowerInfoObject> myTowerInformation;
    protected GSONFileReader myFileReader;
    protected GSONFileWriter myFileWriter;

    private TowerTileGrid myTowerLocationByGrid;
    private GridPane myTowerTiles;

    private double myFieldWidth;
    private double myFieldHeight;

    private boolean myPausedFlag;
    private String myCurrentGameName;
    private SimpleDoubleProperty myCurrentLevelProperty;
    private SimpleDoubleProperty myWinStatus;
    
    public MainEngineManager () {
        myReadyToPlay = new AtomicBoolean(false);
        myEnemyGroup = new RangeRestrictedCollection<>();
        myTowerGroup = new RangeRestrictedCollection<>();
        myProjectileGroup = new RangeRestrictedCollection<>();
        myValidRegions = new GridPane();
        myTowerInformation = new ArrayList<>();
        myEnemiesToAdd = new LinkedList<>();
        myTimeline = createTimeline();
        myCurrentLevelIndex = -1;
        myNodeToTower = new HashMap<>();
        myPrototypeTowerMap = new HashMap<>();
        myFileReader = new GSONFileReader();
        myFileWriter = new GSONFileWriter();
        myUpdateRate = 1;
        myGold = new SimpleDoubleProperty();
        myHealth = new SimpleDoubleProperty();
        myLastUpdateTime = -1;
        myPausedFlag = true;
        myEarthquakeMagnitude = new SimpleDoubleProperty();
        myTowerLocationByGrid = new TowerTileGrid(20, 20);
        myTowerTiles = createGameSizedGridPane();
        myCurrentLevelProperty = new SimpleDoubleProperty(1);
        myWinStatus = new WinStatusProperty();
    }

    public void setEarthquakeMagnitude (double magnitude) {
        myEarthquakeMagnitude.set(magnitude);
    }

    @Override
    public double getEarthquakeMagnitude () {
        return myEarthquakeMagnitude.get();
    }

    @Override
    public GridPane getReferencePane () {
        return myTowerTiles;
    }

    @Override
    public TowerTileGrid getExistingTowerTiles () {
        return myTowerLocationByGrid;
    }

    public MainEngineManager (Pane engineGroup) {
        this();
        addGroups(engineGroup);
    }

    protected void addGroups (Pane engineGroup) {
        engineGroup.getChildren().add(myTowerGroup);
        engineGroup.getChildren().add(myProjectileGroup);
        engineGroup.getChildren().add(myEnemyGroup);
        myFieldWidth = engineGroup.getWidth();
        myFieldHeight = engineGroup.getHeight();
    }

    @Override
    public double getMyGold () {
        return myGold.get();
    }

    public DoubleProperty getGoldProperty () {
        return myGold;
    }

    @Override
    public void setMyGold (double value) {
        myGold.set(value);
    }

    @Override
    public double getMyHealth () {
        return myHealth.get();
    }

    public DoubleProperty getHealthProperty () {
        return myHealth;
    }

    @Override
    public void setMyHealth (double value) {
        myHealth.set(value);
    }

    public void revertToOriginalSpeed () {
        changeRunSpeed(1);
    }

    public void changeRunSpeed (double magnitude) {
        if (magnitude > 0.0) {
            myUpdateRate = magnitude;
        }
    }

    public String getTowerName (ImageView node) {
        BaseTower tower = myNodeToTower.get(node);
        return tower == null ? null : tower.toString();
    }

    public void removeTower (ImageView node) {
        BaseTower tower = myNodeToTower.get(node);
        myNodeToTower.remove(node);
        myTowerGroup.remove(tower);
        setTowerTileStatus(tower, false);
    }

    public ImageView addTower (String identifier, double x, double y) {
        BaseTower prototypeTower = myPrototypeTowerMap.get(identifier);
        BaseTower newTower = (BaseTower) prototypeTower.copy();
        CenteredImageView newTowerNode = newTower.getNode();
        newTowerNode.setXCenter(x);
        newTowerNode.setYCenter(y);
        newTowerNode.setVisible(true);
        myTowerGroup.add(newTower);
        myNodeToTower.put(newTowerNode, newTower);

        setTowerTileStatus(newTower, true);
        newTower.addObserver(this);
        return newTowerNode;
    }

    private void setTowerTileStatus (BaseTower tower, boolean towerTileStatus) {
        Node towerNode = tower.getNode();
        Collection<Node> towerTiles = getIntersectingTowerTileNode(towerNode,
                                                                   myTowerTiles.getChildren());
        for (Node tileNode : towerTiles) {
            Tile tile = (Tile) tileNode;
            int row = tile.getRow();
            int col = tile.getColumn();
            myTowerLocationByGrid.setTowerTile(row, col, towerTileStatus);
        }
    }

    private Collection<Node> getIntersectingTowerTileNode (Node towerNode,
                                                           Collection<Node> nodeList) {
        List<Node> towerTiles = nodeList.stream()
                .filter(node -> node.intersects(towerNode.getBoundsInLocal()))
                .collect(Collectors.toList());
        return towerTiles;
    }

    private Timeline createTimeline () {
        EventHandler<ActionEvent> frameEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                if (myReadyToPlay.get()) {
                    double frameStart = System.currentTimeMillis();
                    double adjustedUpdateInterval = FRAME_DURATION
                                                    / myUpdateRate;
                    if (myUpdateRate >= 1.0
                        || frameStart - myLastUpdateTime >= adjustedUpdateInterval) {
                        myLastUpdateTime = frameStart;
                        double updatedTime = System.currentTimeMillis();
                        do {
                            double updateStart = System.currentTimeMillis();
                            gameUpdate();
                            double updateEnd = System.currentTimeMillis();
                            updatedTime += Math.max(updateEnd - updateStart,
                                                    adjustedUpdateInterval);
                        } while (updatedTime - frameStart < FRAME_DURATION);
                    }
                    myTowerLocationByGrid.setAsNotChanged();
                }
            }
        };
        KeyFrame keyFrame = new KeyFrame(Duration.millis(FRAME_DURATION),
                                         frameEvent);
        Timeline timeline = new Timeline(FPS, keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    private void gameUpdate () {
        addEnemies();
        updateActors(myTowerGroup);
        updateActors(myEnemyGroup);
        updateActors(myProjectileGroup);
        duration--;
        if (myEnemyGroup.getChildren().size() <= 0) {
            onLevelEnd();
        }
        myTowerGroup.clearAndExecuteRemoveBuffer();
        myEnemyGroup.clearAndExecuteRemoveBuffer();
        myProjectileGroup.clearAndExecuteRemoveBuffer();
        if(myHealth.get() <= 0){
        	onGameLose();
        }
    }

    private void onGameLose() {
    	myHealth.set(0);
    	myWinStatus.set(-1);
    	myTowerGroup.clear();
    	myEnemyGroup.clear();
    	myProjectileGroup.clear();
    	pause();
    }
    
    protected void onLevelEnd () {
        duration = 0;
        pause();
        myProjectileGroup.clear();
        if( myLevels != null ) {
        	loadNextLevel();
        }    
    }

    private void addEnemies () {

        if (duration <= 0) {
            duration += myIntervalBetweenEnemies;
            BaseEnemy enemy = myEnemiesToAdd.poll();
            if (enemy == null)
                return;
            enemy.addObserver(this);
            myEnemyGroup.add(enemy);
        }
    }

    private void updateActors (
                               RangeRestrictedCollection<? extends BaseActor> actorGroup) {
        for (BaseActor actor : actorGroup) {
            if (actor.isDead()) {
                actorGroup.addActorToRemoveBuffer(actor);
            }
            else {
                actor.update(this);
            }
            if (!isInRangeOfField(actor)) {
                actor.died();
            }
        }
    }

    private boolean isInRangeOfField (BaseActor actor) {
        double actorX = actor.getX();
        double actorY = actor.getY();
        return 0 <= actorX && actorX <= myFieldWidth && 0 <= actorY
               && actorY <= myFieldHeight;
    }

    @Override
    public List<BaseActor> getRequiredActors (BaseActor actor,
                                              Class<? extends BaseActor> infoType) {
        List<BaseActor> list = new ArrayList<>();

        if (BaseEnemy.class.isAssignableFrom(infoType)) {
            list = myEnemyGroup.getActorsInRange(actor);
        }
        if (BaseTower.class.isAssignableFrom(infoType)) {
            list = myTowerGroup.getActorsInRange(actor);
        }
        if (BaseProjectile.class.isAssignableFrom(infoType)) {
            list = myProjectileGroup.getActorsInRange(actor);
        }

        return list;
    }

    public boolean checkNewPath () {
        return myTowerLocationByGrid.hasBeenChanged();
    }

    public void pause () {
        myTimeline.pause();
        myPausedFlag = true;
    }

    public void resume () {
        myTimeline.play();
        myPausedFlag = false;
    }

    public Collection<TowerInfoObject> getAllTowerTypeInformation () {
        if (!myReadyToPlay.get()) { return null; }
        return myTowerInformation;
    }

    public void initializeGame (String directory) {
        String[] splitDirectory = directory.split(DIRECTORY_SPLIT);
        myCurrentGameName = splitDirectory[splitDirectory.length - 1];
        myTowerGroup.clear();
        myEnemyGroup.clear();
        myProjectileGroup.clear();
        String correctedDirectory = directory += DIRECTORY_SPLIT;
        myReadyToPlay.set(false);
        loadTowers(correctedDirectory);
        loadLevelFile(correctedDirectory);
        loadLocations(correctedDirectory);
        loadGameSettings(correctedDirectory);
        myReadyToPlay.set(true);
        loadNextLevel();
    }

    private void loadGameSettings (String directory) {
        GeneralSettingsWrapper settingsWrapper = myFileReader.readGeneralSettingsWrapper(directory);
        myGold.set(settingsWrapper.getStartingCash());
        myHealth.set(settingsWrapper.getStartingHealth());
    }

    private void loadLocations (String dir) {
        boolean[][] validRegions = myFileReader
                .readTowerRegionsFromGameDirectory(dir);
        myTowerLocationByGrid = new TowerTileGrid(validRegions.length,
                                                  validRegions[0].length);
        myValidRegions = createGameSizedGridPane();
        myTowerTiles = createGameSizedGridPane();
        for (int row = 0; row < validRegions.length; row++) {
            for (int col = 0; col < validRegions[0].length; col++) {
                if (validRegions[row][col]) {
                    Tile tile = new Tile(row, col);
                    tile.setVisible(false);
                    myValidRegions.add(tile, col, row);
                }
                Tile towerTile = new Tile(row, col);
                towerTile.setVisible(false);
                myTowerTiles.add(towerTile, col, row);
            }
        }
    }

    private GridPane createGameSizedGridPane () {
        GridPane pane = new GridPane();
        pane.setPrefSize(BuildingPane.DRAW_SCREEN_WIDTH,
                         AuthorController.SCREEN_HEIGHT);
        return pane;
    }

    public boolean validateTower (double x, double y) {
        return !(listCollidesWith(myTowerGroup.getChildren(), x, y))
               && listCollidesWith(myValidRegions.getChildren(), x, y);
    }

    private boolean listCollidesWith (List<Node> list, double x, double y) {
        return list.stream().filter(node -> node.contains(x, y)).count() > 0;
    }

    public boolean checkGold (TowerInfoObject towerInfoObject) {
        return towerInfoObject.getBuyCost() <= myGold.get();
    }

    public void loadTowers (String directory) {
        List<TowerUpgradeGroup> availableTowers = myFileReader
                .readTowersFromGameDirectory(directory);
        for (TowerUpgradeGroup towerGroup : availableTowers) {
            TowerInfoObject prevInfoObject = null;
            for (BaseTower tower : towerGroup) {
                String towerName = tower.toString();
                myPrototypeTowerMap.put(towerName, tower);
                TowerInfoObject currentInfoObject =
                        new TowerInfoObject(
                                            towerName, tower.getImagePath(), tower.getBuyCost(),
                                            tower.getSellCost(), tower.getRangeProperty());
                if (prevInfoObject != null) {
                    prevInfoObject.setNextTower(currentInfoObject);
                }
                else {
                    myTowerInformation.add(currentInfoObject);
                }
                prevInfoObject = currentInfoObject;
            }
            if (prevInfoObject != null) {
                prevInfoObject.setNextTower(new NullTowerInfoObject());
            }
        }
    }

    private void loadLevelFile (String directory) {
        myLevels = myFileReader.readLevelsFromGameDirectory(directory);
    }

    private void loadNextLevel () {
        myCurrentLevelIndex += 1;
        if (myCurrentLevelIndex < myLevels.size()) {
        	myCurrentLevelProperty.set(myCurrentLevelIndex + 1);
            loadLevel(myLevels.get(myCurrentLevelIndex));
        }
        else {
        	myWinStatus.set(1);
        }
    }

    public SimpleDoubleProperty getWinStatus() {
    	return myWinStatus;	
    }
    
    public SimpleDoubleProperty getCurrentLevelProperty() {
    	return myCurrentLevelProperty;
    }
    
    public void loadLevel (BaseLevel level) {
        int levelDuration = level.getDuration();
        Collection<EnemyCountPair> enemies = level.getEnemyCountPairs();
        for (EnemyCountPair enemyPair : enemies) {
            BaseEnemy enemy = enemyPair.getMyEnemy();
            for (int count = 0; count < enemyPair.getMyNumEnemies(); count++) {
                BaseEnemy newEnemy = (BaseEnemy) enemy.copy();
                myEnemiesToAdd.add(newEnemy);
            }
        }
        myIntervalBetweenEnemies = levelDuration * FPS / myEnemiesToAdd.size();
    }

    public boolean isRunning() {
    	return (myTimeline.statusProperty().get() == Status.RUNNING);
    }
    
    public void loadAuthoringLevel (BaseLevel level) {
        pause();
        myHealth.set(Double.MAX_VALUE);
        myEnemyGroup.clear();
        myEnemiesToAdd.clear();
        loadLevel(level);
        myReadyToPlay.set(true);

    }

    @Override
    public void update (Observable o, Object arg) {
        if (arg instanceof UpdateObject) {
            ((UpdateObject) arg).update(this);
        }
        else if (o instanceof BaseActor && arg != null) {
            if (arg instanceof BaseTower) {
                myTowerGroup.add((BaseTower) arg);
            }
            else if (arg instanceof BaseEnemy) {
                myTowerGroup.add((BaseTower) arg);
            }
            else if (arg instanceof BaseProjectile) {
                myProjectileGroup.add((BaseProjectile) arg);
            }
        }
    }

    public void saveState (String directory, String fileName) {
        if (myPausedFlag) {
            String joinedFileName = directory + DIRECTORY_SPLIT + fileName + JSON_EXTENSION;
            try {
                List<DataWrapper> wrappedTowers = wrapTowers();
                GameStateWrapper gameState =
                        new GameStateWrapper(
                                             myCurrentGameName, myCurrentLevelIndex,
                                             myHealth.get(),
                                             myGold.get(), wrappedTowers);
                myFileWriter.writeGameStateToJSon(joinedFileName, gameState);
            }
            catch (Exception ex) {
                new ErrorPopup("Error writing state");
            }
        }
    }

    private List<DataWrapper> wrapTowers () {
        ArrayList<DataWrapper> wrappedTowers = new ArrayList<>();
        for (BaseTower tower : myTowerGroup) {
            DataWrapper wrappedTower = new DataWrapper(tower);
            wrappedTowers.add(wrappedTower);
        }
        return wrappedTowers;
    }

    public void clear () {
        if (myPausedFlag) {
            myTowerGroup.clear();
            myEnemyGroup.clear();
            myProjectileGroup.clear();
            myLastUpdateTime = -1;
            myEnemiesToAdd.clear();
            myNodeToTower.clear();
            myTowerInformation.clear();
        }
    }

    public void loadState (String gameFile) {
        if (myPausedFlag) {
            try {
                clear();
                GameStateWrapper gameState = myFileReader
                        .readGameStateFromJSon(gameFile);
                if (gameState.getName().equals(myCurrentGameName)) {
                    myGold.set(gameState.getMoney());
                    myHealth.set(gameState.getHealth());
                    myCurrentLevelIndex = gameState.getLevel();
                    myCurrentLevelProperty.set(myCurrentLevelIndex + 1);
                    loadLevel(myLevels.get(myCurrentLevelIndex));
                    List<DataWrapper> towers = gameState.getTowerWrappers();
                    for (DataWrapper wrappedTower : towers) {
                        addTower(wrappedTower.getName(), wrappedTower.getX(), wrappedTower.getY());
                    }
                }
                else {
                    new ErrorPopup(INCORRECT_SAVE_STATE_MESSAGE);
                }
            }
            catch (Exception ex) {
                new ErrorPopup(UNKNOWN_SAVE_STATE_FILE_MESSAGE);
            }
        }
    }

    public ImageView upgrade (ImageView n, String name) {
        removeTower(n);
        return addTower(name, ((CenteredImageView) n).getXCenter(),
                        ((CenteredImageView) n).getYCenter());

    }

    public void sellTower (ImageView n) {
        myGold.set(myNodeToTower.get(n).getSellCost() + myGold.get());
        removeTower(n);
    }
}
