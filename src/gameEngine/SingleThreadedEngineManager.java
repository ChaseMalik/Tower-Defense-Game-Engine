package gameEngine;

import gameAuthoring.scenes.actorBuildingScenes.TowerUpgradeGroup;
import gameAuthoring.scenes.levelBuilding.EnemyCountPair;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseProjectile;
import gameEngine.actors.BaseTower;
import gameEngine.actors.InfoObject;
import gameEngine.levels.BaseLevel;

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

import utilities.GSON.GSONFileReader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SingleThreadedEngineManager implements Observer {

    private static final int FPS = 30;
    private static final double ONE_SECOND_IN_MILLIS = 1000.0;
    private static final double FRAME_DURATION = 1000.0 / 30;

    private double myLastUpdateTime;
    private Timeline myTimeline;
    private double myUpdateRate;
    private AtomicBoolean myPauseRequested;
    private AtomicBoolean myReadyToPlay;
    private RangeRestrictedCollection<BaseTower> myTowerGroup;
    private RangeRestrictedCollection<BaseEnemy> myEnemyGroup;
    private RangeRestrictedCollection<BaseProjectile> myProjectileGroup;

    private List<BaseLevel> myLevels;
    private BaseLevel myCurrentLevel;
    private int myCurrentLevelIndex;

    private Map<String, BaseTower> myPrototypeTowerMap;
    private double myIntervalBetweenEnemies;
    private Queue<BaseEnemy> myEnemiesToAdd;

    private Map<Node, BaseTower> myNodeToTower;
    private Collection<TowerInfoObject> myTowerInformation;
    private GSONFileReader myFileReader;
    private Group myMainGroup;

    public SingleThreadedEngineManager(Group engineGroup) {
        myReadyToPlay = new AtomicBoolean(false);
        myPauseRequested = new AtomicBoolean(false);
        myEnemyGroup = new RangeRestrictedCollection<>();
        myTowerGroup = new RangeRestrictedCollection<>();
        myProjectileGroup = new RangeRestrictedCollection<>();
        engineGroup.getChildren().add(myTowerGroup);
        engineGroup.getChildren().add(myProjectileGroup);
        engineGroup.getChildren().add(myEnemyGroup);
        myTowerInformation = new ArrayList<>();
        myEnemiesToAdd = new LinkedList<>();
        myTimeline = createTimeline();
        //myTimeline.play();
        myCurrentLevelIndex = -1;
        myNodeToTower = new HashMap<>();
        myPrototypeTowerMap = new HashMap<>();
        myFileReader = new GSONFileReader();
        myMainGroup = engineGroup;
        myUpdateRate = 1.0;
    }

    public void fastForward() {
        changeRunSpeed(4);
    }

    public void revertToOriginalSpeed() {
        changeRunSpeed(1);
    }

    public void changeRunSpeed(double magnitude) {
        myUpdateRate = magnitude;
    }

    public String getTowerName(ImageView node) {
        BaseTower tower = myNodeToTower.get(node);
        return tower == null ? null : tower.toString();
    }

    public void removeTower(ImageView node) {
        BaseTower tower = myNodeToTower.get(node);
        myNodeToTower.remove(node);
        myTowerGroup.remove(tower);
    }

    public ImageView addTower(String identifier, double x, double y) {
        BaseTower prototypeTower = myPrototypeTowerMap.get(identifier);
        boolean towerValidity = prototypeTower != null && myCurrentLevel.validateTower(prototypeTower, x, y);
        if(towerValidity){
            BaseTower newTower = (BaseTower)prototypeTower.copy();
            ImageView newTowerNode = newTower.getNode();
            newTowerNode.setX(x);
            newTowerNode.setY(y);
            newTowerNode.setVisible(true);
            myTowerGroup.add(newTower);
            myNodeToTower.put(newTowerNode, newTower);
            return newTowerNode;
        }
        return null;
    }

    private Timeline createTimeline() {
        EventHandler<ActionEvent> frameEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if ( myReadyToPlay.get()) {
                    double now = System.nanoTime();
                    double adjustedUpdateInterval = FRAME_DURATION
                            / myUpdateRate;
                    double updateTimeDifference = now - myLastUpdateTime;
                    while (updateTimeDifference > adjustedUpdateInterval) {
                        double updateStart = System.nanoTime();
                        gameUpdate();
                        double updateEnd = System.nanoTime();
                        double updateDuration = updateEnd - updateStart;
                        double updateDurationCeiling = Math.max(updateDuration,
                                                                adjustedUpdateInterval);
                        myLastUpdateTime += updateDurationCeiling;
                        updateTimeDifference -= updateDurationCeiling;
                    }
                }
            }
        };
        KeyFrame keyFrame = new KeyFrame(Duration.millis(FRAME_DURATION),
                                         frameEvent);
        Timeline timeline = new Timeline(FPS, keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        return timeline;
    }

    private void gameUpdate() {
        updateActors(myTowerGroup);
        updateActors(myEnemyGroup);
        updateActors(myProjectileGroup);
        addEnemies();
        if(myEnemyGroup.getChildren().size() <= 0) {
            onLevelEnd();
        }
        myTowerGroup.clearAndExecuteRemoveBuffer();
        myEnemyGroup.clearAndExecuteRemoveBuffer();
        myProjectileGroup.clearAndExecuteRemoveBuffer();
    }

    private void onLevelEnd() {
        myReadyToPlay.set(false);
        loadNextLevel();
        myReadyToPlay.set(true);
    }

    private void addEnemies() {
        double duration = 0;
        while(duration < FRAME_DURATION) {
            duration += myIntervalBetweenEnemies;
            BaseEnemy enemy = myEnemiesToAdd.poll();
            myEnemyGroup.add(enemy);
        }
    }

    private void updateActors(RangeRestrictedCollection<? extends BaseActor> actorGroup) {
        for (BaseActor actor : actorGroup) {
            if(actor.isDead()){
                actorGroup.addActorToRemoveBuffer(actor);
            }
            else {
                InfoObject requiredInfo = getRequiredInformation(actor);
                actor.update(requiredInfo);
            }	
        }
    }

    private InfoObject getRequiredInformation(BaseActor actor) {
        Collection<Class<? extends BaseActor>> infoTypes = actor.getTypes();
        List<BaseActor> enemyList = new ArrayList<>();
        List<BaseActor> towerList = new ArrayList<>();
        List<BaseActor> projectileList=new ArrayList<>();
        for (Class<? extends BaseActor> infoType : infoTypes) {
            if (BaseEnemy.class.isAssignableFrom(infoType)) {
                enemyList = myEnemyGroup.getActorsInRange(actor);
            }
            if (BaseTower.class.isAssignableFrom(infoType)) {
                towerList = myTowerGroup.getActorsInRange(actor);
            }
            if (BaseProjectile.class.isAssignableFrom(infoType)){
                projectileList= myProjectileGroup.getActorsInRange(actor);
            }
        }
        return new InfoObject(enemyList, towerList, projectileList);
    }

    public void pause() {
        //myPauseRequested.set(true);
        myTimeline.pause();
    }

    public void resume() {
        //myPauseRequested.set(false);
        myTimeline.play();
    }

    public Collection<TowerInfoObject> getAllTowerTypeInformation() {
        if(!myReadyToPlay.get()) {
            return null;
        }
        return myTowerInformation;
    }

    public void initializeGame(String directory) {
        String correctedDirectory = directory += "/";
        myReadyToPlay.set(false);
        loadTowers(correctedDirectory);
        loadLevelFile(correctedDirectory);
        myReadyToPlay.set(true);
        loadNextLevel();
    }

    public void loadTowers(String directory) {
        List<TowerUpgradeGroup> availableTowers = myFileReader.readTowersFromGameDirectory(directory);
        for (TowerUpgradeGroup towerGroup : availableTowers) {
            TowerInfoObject prevInfoObject = null;
            for (BaseTower tower : towerGroup) {
                String towerName = tower.toString();
                myPrototypeTowerMap.put(towerName, tower);
                TowerInfoObject currentInfoObject = new TowerInfoObject(towerName, tower.getImagePath(), 0);
                if(prevInfoObject != null) {
                    prevInfoObject.setNextTower(currentInfoObject);
                }
                else{
                    myTowerInformation.add(currentInfoObject);
                }
                prevInfoObject = currentInfoObject;
            }
            if(prevInfoObject != null) {
                prevInfoObject.setNextTower(new NullTowerInfoObject());
            }
        }
    }

    private void loadLevelFile(String directory) {
        myLevels = myFileReader.readLevelsFromGameDirectory(directory);		
    }

    private void loadNextLevel() {
        myCurrentLevelIndex += 1;
        loadLevel(myLevels.get(myCurrentLevelIndex));
    }

    private void loadLevel(BaseLevel level) {
        int levelDuration = level.getDuration();
        Collection<EnemyCountPair> enemies = level.getEnemyCountPairs();
        for(EnemyCountPair enemyPair : enemies){
            BaseEnemy enemy = enemyPair.getMyEnemy();
            for(int count = 0; count < enemyPair.getMyNumEnemies(); count ++){
                BaseEnemy newEnemy = (BaseEnemy)enemy.copy();
                myEnemiesToAdd.add(newEnemy);
            }		
        }
        myIntervalBetweenEnemies = levelDuration*ONE_SECOND_IN_MILLIS/myEnemiesToAdd.size();
        myCurrentLevel = level;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof BaseActor && arg != null) {
            if (arg instanceof BaseTower) {
                myTowerGroup.add((BaseTower) arg);
            } else if (arg instanceof BaseEnemy) {
                myEnemyGroup.add((BaseEnemy) arg);
            } else if (arg instanceof BaseProjectile) {
                myProjectileGroup.add((BaseProjectile) arg);
            }
        }
    }
}
