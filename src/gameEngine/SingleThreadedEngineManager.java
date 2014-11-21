package gameEngine;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseProjectile;
import gameEngine.actors.BaseTower;
import gameEngine.levels.BaseLevel;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.util.Duration;
import utilities.errorPopup.ErrorPopup;

public class SingleThreadedEngineManager implements Observer {

	private static final int FPS = 30;
	private static final double ONE_SECOND_IN_MILLIS = 1000.0;
    private Timeline myTimeline;
    
	public SingleThreadedEngineManager(Group engineGroup){
		
	}
		
	private Timeline createTimeline() {
		KeyFrame keyFrame = new KeyFrame(Duration.millis(ONE_SECOND_IN_MILLIS/FPS));
		Timeline timeline = new Timeline();
		return null;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
