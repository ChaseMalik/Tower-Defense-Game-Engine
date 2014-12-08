package gameEngine.actors.behaviors;

import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;

import java.util.List;

import javafx.geometry.Point2D;

public class EnemyHomingMovement extends BaseMovementBehavior{

	private BaseEnemy myHomedEnemy;
	private Point2D myLastLocationOfEnemy;
	private boolean myEnemyFound;
	private boolean myFirstSetFlag;
	
	EnemyHomingMovement(List<Double> list) {
		super(list);
		myEnemyFound = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(BaseActor actor) {
		
		if(!myFirstSetFlag) {
			//actor.move()
		}
		if(!myEnemyFound){
			List<BaseActor> enemies = actor.getEnemiesInRange(1000);
			double distanceToEnemy = Double.MAX_VALUE;
			BaseActor closestEnemy = null;
			for(BaseActor enemy: enemies) {
				Point2D enemyLocation = new Point2D(enemy.getX(), enemy.getY()); 
				double distance = enemyLocation.distance(actor.getX(), actor.getY());
				if(distance < distanceToEnemy) {
					distanceToEnemy = distance;
					closestEnemy = enemy;
				}
			}
			//myHomedEnemy = 
		}
		
	}

	@Override
	public IBehavior copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
