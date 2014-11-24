//package gameEngine;
//
//import gameEngine.actors.BaseActor;
//import gameEngine.actors.BaseEnemy;
//import gameEngine.actors.BaseProjectile;
//import gameEngine.actors.BaseTower;
//import gameEngine.levels.BaseLevel;
//import java.util.List;
//import java.util.Map;
//import java.util.Observable;
//import java.util.Observer;
//import java.util.concurrent.atomic.AtomicBoolean;
//import java.util.concurrent.atomic.AtomicInteger;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import utilities.errorPopup.ErrorPopup;
//
//public class MainGameManager implements Observer {
//
//	private static final double ONE_SECOND_IN_NANO = 1000000000.0;
//	private static final int FPS = 30;
//
//	private AtomicBoolean myIsRunning;
//	private AtomicBoolean myPauseRequested;
//	private AtomicBoolean readyToPlay;
//	private AtomicBoolean myIsPaused;
//
//	private double myRenderInterval;
//	private double myUpdateInterval;
//	// 1 is normal speed
//	private AtomicInteger myUpdateSpeed;
//
//	private double myLastUpdateTime;
//	private double myLastRenderTime;
//	private List<BaseEnemy> myEnemies;
//	private List<BaseTower> myTowers;
//	private List<BaseProjectile> myProjectiles;
//	private Map<Class<? extends BaseActor>, BaseActor> myAvailableActors;
//
//	private static final int ENEMY_INDEX = 0;
//	private static final int PROJECTILE_INDEX = 1;
//	private static final int TOWER_INDEX = 2;
//
//	private Group myTowerGroup;
//	private Group myProjectileGroup;
//	private Group myEnemyGroup;
//	private double levelDuration;
//
//	private static final String INVALID_TOWER_ERROR = "Invalid Tower";
//
//	private BaseLevel myCurrentLevel;
//	private List<BaseActor> myTowersToAdd;
//
//	public MainGameManager(Group engineGroup) {
//		myIsRunning = new AtomicBoolean(false);
//		readyToPlay = new AtomicBoolean(false);
//		myPauseRequested = new AtomicBoolean(false);
//		myIsPaused = new AtomicBoolean(true);
//
//		myRenderInterval = ONE_SECOND_IN_NANO / FPS;
//		myUpdateInterval = myRenderInterval;
//		myUpdateSpeed = new AtomicInteger(1);
//
//		myLastUpdateTime = System.nanoTime();
//		myLastRenderTime = System.nanoTime();
//
//		myTowerGroup = new Group();
//		myProjectileGroup = new Group();
//		myEnemyGroup = new Group();
//
//		addToGroup(engineGroup, myTowerGroup);
//		addToGroup(engineGroup, myProjectileGroup);
//		addToGroup(engineGroup, myEnemyGroup);
//	}
//
//	private void addToGroup(Group group, Node node) {
//		group.getChildren().add(node);
//	}
//
//	/**
//	 * Fast forwards the game by a specific amount
//	 */
//	public void fastForward() {
//		// TODO: change
//		speedUp(4);
//	}
//
//	/**
//	 * Reverts to normal speed for the rate at which the game runs
//	 */
//	public void revertToNormalSpeed() {
//		speedUp(1);
//	}
//
//	/**
//	 * Speeds up the rate at which the game runs by a specified amount
//	 * 
//	 * @param magnitude
//	 *            Speed up factor
//	 */
//	public void speedUp(int magnitude) {
//		myUpdateSpeed.set(magnitude);
//	}
//
//	/**
//	 * Loads the game state
//	 *
//	 * @param fileName
//	 *            Name of file to load
//	 */
//	public void loadState(String fileName) {
//	}
//
//	/**
//	 * Saves the game's current state in the file specified by fileName
//	 *
//	 * @param fileName
//	 *            Name of save destination
//	 */
//	public void saveState(String fileName) {
//	}
//
//	/**
//	 * Initializes the game environment
//	 *
//	 * @param fileName
//	 *            Name of game environment file to initialize
//	 */
//	public void initializeGame(String fileName) {
//
//	}
//
//	/**
//	 * Adds a tower placed by the user. Will be actually added to the list of
//	 * active towers on the next frame
//	 * 
//	 * @param identifier Identifier for the tower
//	 * @param x X location
//	 * @param y Y location
//	 * @return Whether tower was able to be placed at the specified location
//	 */
//	public synchronized boolean addTower(String identifier, double x, double y) {
//		BaseActor tower = myCurrentLevel.createTower(identifier, x, y);
//		if (tower == null) {
//			return false;
//		}
//		myTowersToAdd.add(tower);
//		return false;
//	}
//
//	private void addEnemy(BaseEnemy e) {
//		myEnemies.add(e);
//	}
//
//	private void addTower(BaseTower t) {
//		t.addObserver(this);
//		myTowers.add(t);
//	}
//
//	private void addProjectile(BaseProjectile p) {
//		myProjectiles.add(p);
//	}
//
//	private void createNewActor(Class<? extends BaseActor> actorType) {
//		BaseActor exampleActor = myAvailableActors.get(actorType);
//		BaseActor newActor = exampleActor.copy();
//		// newActor.setX
//	}
//
//	/**
//	 * Plays or resumes the game
//	 */
//	public void start() {
//		if (readyToPlay.get()) {
//			myIsRunning.set(true);
//			Thread gameLoop = new Thread(() -> gameLoop());
//			gameLoop.start();
//		} else {
//			new ErrorPopup("Not yet ready to play");
//		}
//	}
//
//	/**
//	 * Pauses the game
//	 */
//	public void pause() {
//		myPauseRequested.set(true);
//	}
//
//	public void resume() {
//		myPauseRequested.set(false);
//	}
//
//	private void gameLoop() {
//		while (myIsRunning.get()) {
//			double now = System.nanoTime();
//			if (!myPauseRequested.get() && readyToPlay.get()) {
//				myIsPaused.set(false);
//				double adjustedUpdateInterval = myUpdateInterval
//						/ myUpdateSpeed.get();
//				double updateTimeDifference = now - myLastUpdateTime;
//				double timeBetweenUpdateAndRender = now - myLastRenderTime;
//				// Allow for catchup in the case of inaccurate thread waking up.
//				do {
//					double updateStart = System.nanoTime();
//					update();
//					double updateEnd = System.nanoTime();
//					myLastUpdateTime += Math.max(adjustedUpdateInterval,
//							updateEnd - updateStart);
//					updateTimeDifference = now - myLastUpdateTime;
//					timeBetweenUpdateAndRender = updateEnd - myLastRenderTime;
//				} // get rid of second check if frame dropping is unimportant.
//					// Allows for more
//					// accurate updates before render
//				while (updateTimeDifference > adjustedUpdateInterval
//						&& timeBetweenUpdateAndRender < myRenderInterval);
//				if (now - myLastRenderTime >= myRenderInterval) {
//					render();
//					myLastRenderTime = now;
//				}
//
//				while (now - myLastRenderTime < myRenderInterval
//						&& now - myLastUpdateTime < adjustedUpdateInterval) {
//					Thread.yield();
//					try {
//						Thread.sleep(0, 500);
//					} catch (InterruptedException e) {
//						// probably fine
//					}
//					now = System.nanoTime();
//				}
//			} else {
//				myIsPaused.set(true);
//				// Thread.yield();
//				try {
//					Thread.sleep(0, 500);
//				} catch (InterruptedException e) {
//					// Probably fine
//				}
//			}
//		}
//	}
//
//	private void update() {
//		// addNewActors();
//
//		updateActors(myEnemyGroup);
//		updateActors(myProjectileGroup);
//		updateActors(myTowerGroup);
//	}
//
//	private void updateActors(Group group) {
//		List<Node> children = group.getChildren();
//		for (Node child : children) {
//			//BaseActor actor = (BaseActor) child;
//			// actor.update();
//		}
//	}
//
//	private void render() {
//
//	}
//
//	/**
//	 * Quits the game
//	 */
//	public void quit() {
//		myIsRunning.set(false);
//	}
//
//	public synchronized void loadLevel(BaseLevel level) {
//		readyToPlay.set(false);
//		while (!myIsPaused.get()) {
//			try {
//				Thread.sleep(0, 500);
//			} catch (InterruptedException e) {
//				// Probably fine
//			}
//		}
//		myTowerGroup.getChildren().clear();
//		myProjectileGroup.getChildren().clear();
//		myEnemyGroup.getChildren().clear();
//
//		// load
//		readyToPlay.set(true);
//	}
//
//	@Override
//	public void update(Observable arg0, Object projectile) {
//		if (projectile instanceof BaseProjectile) {
//			addProjectile((BaseProjectile) projectile);
//		}
//	}
//}
