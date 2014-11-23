package gameEngine;

import gameAuthoring.scenes.actorBuildingScenes.TowerUpgradeGroup;
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
	
	public SingleThreadedEngineManager(Group engineGroup) {
		myReadyToPlay = new AtomicBoolean(false);
		myPauseRequested = new AtomicBoolean(false);
		myTowerGroup = new RangeRestrictedCollection<>();
		myProjectileGroup = new RangeRestrictedCollection<>();
		engineGroup.getChildren();

		myEnemiesToAdd = new LinkedList<>();
		myTimeline = createTimeline();
		myTimeline.play();
		myCurrentLevelIndex = 0;
		myNodeToTower = new HashMap<>();
		myFileReader = new GSONFileReader();
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

	public String getTowerName(Node node) {
		BaseTower tower = myNodeToTower.get(node);
		return tower == null ? null : tower.toString();
	}
	
	public void removeTower(Node node) {
		BaseTower tower = myNodeToTower.get(node);
		myNodeToTower.remove(node);
		myTowerGroup.remove(tower);
	}
	
	public Node addTower(String identifier, double x, double y) {
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
				if (!myPauseRequested.get() && myReadyToPlay.get()) {
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
		return timeline;
	}

	private void gameUpdate() {
		updateTowers();
		updateEnemies();
		updateProjectile();
		addEnemies();
		if(myEnemyGroup.getChildren().size() <= 0) {
			onLevelEnd();
		}
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
	
	private void updateTowers() {
		for (BaseTower tower : myTowerGroup) {
			InfoObject requiredInfo = getRequiredInformation(tower);
			tower.update(requiredInfo);
		}
	}

	private void updateEnemies() {
		for (BaseEnemy enemy : myEnemyGroup) {
			InfoObject requiredInfo = getRequiredInformation(enemy);
			enemy.update(requiredInfo);
		}
	}

	private void updateProjectile() {
		for (BaseProjectile projectile : myProjectileGroup) {
			InfoObject requiredInfo = getRequiredInformation(projectile);
			projectile.update(requiredInfo);
			projectileHitDetection(projectile);
		}
	}

	private void projectileHitDetection(BaseProjectile projectile) {
		for (BaseEnemy enemy : myEnemyGroup) {
			if (isCollided(projectile, enemy)) {
				// Do stuff
			}
		}
	}

	private boolean isCollided(BaseActor actor, BaseActor otherActor) {
		Node actorNode = actor.getNode();
		Node otherNode = otherActor.getNode();
		return actorNode.isVisible() && otherNode.isVisible()
				&& actorNode.intersects(otherNode.getBoundsInLocal());
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
		myPauseRequested.set(true);
	}

	public void resume() {
		myPauseRequested.set(false);
	}

	public Collection<TowerInfoObject> getAllTowerTypeInformation() {
		if(!myReadyToPlay.get()) {
			return null;
		}
		return myTowerInformation;
	}
	
	public void initializeGame(String directory) {
		String towerFile = null; 
		String levelFile = null;
		myReadyToPlay.set(false);
		loadTowers(towerFile);
		loadLevelFile(levelFile);
		myReadyToPlay.set(true);
	}

	public void loadTowers(String towerFile) {
		List<TowerUpgradeGroup> availableTowers = null; //= myFileReader.readTowerFromFile(towerFile);
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

	private void loadLevelFile(String levelFile) {
		//myFileReader.readLevel(levelFile);
		myLevels = new ArrayList<>();
		
	}

	private void loadNextLevel() {
		myCurrentLevelIndex += 1;
		loadLevel(myLevels.get(myCurrentLevelIndex));
	}
	
	private void loadLevel(BaseLevel level) {
		int levelDuration = level.getDuration();
		Map<BaseEnemy, Integer> enemyMap = level.getEnemyMap();
		for(BaseEnemy enemy : enemyMap.keySet()){
			for(int count = 0; count < enemyMap.get(enemy); count ++){
				BaseEnemy newEnemy = (BaseEnemy)enemy.copy();
				myEnemiesToAdd.add(newEnemy);
			}		
		}
		myIntervalBetweenEnemies = levelDuration*ONE_SECOND_IN_MILLIS/myEnemiesToAdd.size();
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
