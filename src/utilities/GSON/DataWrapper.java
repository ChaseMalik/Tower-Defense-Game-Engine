package utilities.GSON;

import javafx.geometry.Point2D;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.BaseTower;



public class DataWrapper {
	
	private Point2D myPoint;
	private BaseActor myActor;

	public DataWrapper(BaseActor actor, Point2D point){
		myActor = actor;
		myPoint = point;
	}
	
	
	
	public BaseActor getActor(){		
		return myActor;	
	}
	
	
	public Point2D getPoint(){
		return myPoint;	
	}

}
