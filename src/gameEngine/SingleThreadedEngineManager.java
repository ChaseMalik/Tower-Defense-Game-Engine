package gameEngine;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseProjectile;
import gameEngine.actors.BaseTower;
import gameEngine.actors.InfoObject;
import gameEngine.levels.BaseLevel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;


public class SingleThreadedEngineManager implements Observer {

    private static final int FPS = 30;
    // private static final double ONE_SECOND_IN_MILLIS = 1000.0;
    private static final double FRAME_DURATION = 1000.0 / 30;

    private double myLastUpdateTime;
    private Timeline myTimeline;
    private double myUpdateRate;
    private AtomicBoolean myPauseRequested;
    private AtomicBoolean myReadyToPlay;
    private RangeRestrictedCollection<BaseTower> myTowerGroup;
    private RangeRestrictedCollection<BaseEnemy> myEnemyGroup;
    private RangeRestrictedCollection<BaseProjectile> myProjectileGroup;
    
    public SingleThreadedEngineManager (Group engineGroup) {
        myReadyToPlay = new AtomicBoolean(false);
        myPauseRequested = new AtomicBoolean(false);
        myTowerGroup = new RangeRestrictedCollection<>();
        myProjectileGroup = new RangeRestrictedCollection<>();
        
        myTimeline = createTimeline();
        myTimeline.play();
    }

    public void fastForward () {
        changeRunSpeed(4);
    }

    public void revertToOriginalSpeed () {
        changeRunSpeed(1);
    }

    public void changeRunSpeed (double magnitude) {
        myUpdateRate = magnitude;
    }
    
    public boolean addTower(String identifier, double x, double y) {
        return false;
    }
    
    private Timeline createTimeline () {
        EventHandler<ActionEvent> frameEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                if(!myPauseRequested.get() && myReadyToPlay.get()){
                    double now = System.nanoTime();
                    double adjustedUpdateInterval = FRAME_DURATION / myUpdateRate;
                    double updateTimeDifference = now - myLastUpdateTime;
                    while (updateTimeDifference > adjustedUpdateInterval) {
                        double updateStart = System.nanoTime();
                        gameUpdate();
                        double updateEnd = System.nanoTime();
                        double updateDuration = updateEnd - updateStart;
                        double updateDurationCeiling = Math.max(updateDuration, FRAME_DURATION);
                        myLastUpdateTime += updateDurationCeiling;
                        updateTimeDifference -= updateDurationCeiling;
                    }
                }
            }
        };
        KeyFrame keyFrame = new KeyFrame(Duration.millis(FRAME_DURATION), frameEvent);
        Timeline timeline = new Timeline(FPS, keyFrame);
        return timeline;
    }

    private void gameUpdate () {
        updateTowers();
        updateEnemies();
        updateProjectile();
    }

    private void updateTowers() {
        for(BaseTower tower : myTowerGroup) {
            InfoObject requiredInfo = getRequiredInformation(tower);
            tower.update(requiredInfo);
        }
    }
    
    private void updateEnemies() {
        for(BaseEnemy enemy : myEnemyGroup) {
            InfoObject requiredInfo = getRequiredInformation(enemy);
            enemy.update(requiredInfo);
        }
    }
    
    private void updateProjectile() {
        for(BaseProjectile projectile : myProjectileGroup) {
            InfoObject  requiredInfo = getRequiredInformation(projectile);
            projectile.update(requiredInfo);
            projectileHitDetection(projectile);          
        }
    }
    
    private void projectileHitDetection(BaseProjectile projectile) {
        for(BaseEnemy enemy : myEnemyGroup) {
            if(isCollided(projectile, enemy)) {
                //Do stuff
            }
        }
    }
    
    private boolean isCollided(BaseActor actor, BaseActor otherActor) {
        Node actorNode = actor.getNode();
        Node otherNode = otherActor.getNode();
        return actorNode.intersects(otherNode.getBoundsInLocal());
    }
    
    private InfoObject getRequiredInformation(BaseActor actor) { 
        Collection<Class<? extends BaseActor>> infoTypes = actor.getTypes();
        List<BaseActor> enemyList = new ArrayList<>();
        List<BaseActor> towerList = new ArrayList<>();
        for(Class<? extends BaseActor> infoType : infoTypes) {
            if(BaseEnemy.class.isAssignableFrom(infoType)) {
                enemyList = myEnemyGroup.getActorsInRange(actor);
            }
            if(BaseTower.class.isAssignableFrom(infoType)) {
                towerList = myTowerGroup.getActorsInRange(actor);
            }
        }
        return new InfoObject(enemyList, towerList);
    }
    
    public void pause () {
        myPauseRequested.set(true);
    }

    public void resume () {
        myPauseRequested.set(false);
    }

    private void loadLevel(BaseLevel level){
        myReadyToPlay.set(true);
    }
    
    public void loadLevel(String level){
        // load
        myReadyToPlay.set(true);
    }
    
    @Override
    public void update (Observable o, Object arg) {
        if(o instanceof BaseActor && arg != null) {
            if(arg instanceof BaseTower) {
                myTowerGroup.add((BaseTower) arg);
            }
            else if(arg instanceof BaseEnemy) {
                myEnemyGroup.add((BaseEnemy) arg);
            }
            else if(arg instanceof BaseProjectile) {
                myProjectileGroup.add((BaseProjectile) arg);
            }
        }
    }
}
