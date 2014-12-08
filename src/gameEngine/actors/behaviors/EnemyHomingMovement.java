package gameEngine.actors.behaviors;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.VisibilityPoint;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import java.util.List;
import javafx.geometry.Point2D;

public class EnemyHomingMovement extends BaseMovementBehavior{

	private BaseActor myHomedEnemy;
	private boolean myEnemyFound;
	
	EnemyHomingMovement(List<Double> list) {
		super(list);
		myEnemyFound = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(BaseActor actor) {
		
		if(!myEnemyFound){
		        move(actor, myRoute.get(0).getPoint());
		        actor.getNode().setVisible(true);
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
			myHomedEnemy =closestEnemy; 
			myEnemyFound=true;
		}
		if(myHomedEnemy==null || myHomedEnemy.isDead()){
		    actor.killed();
		    return;
		}
	        Point2D current = new Point2D(actor.getX(), actor.getY());
	        Point2D destination = new Point2D(myHomedEnemy.getX(),myHomedEnemy.getY());
	        moveTowards(actor,current,destination);
		
	}

	@Override
	public IBehavior copy() {
		return new EnemyHomingMovement(myList);
	}

}
