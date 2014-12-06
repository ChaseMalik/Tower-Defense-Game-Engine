package utilities.GSON;

import javafx.geometry.Point2D;
import gameEngine.actors.BaseActor;



public class DataWrapper {
	
	private double myX;
	private double myY;
	private BaseActor myActor;

	public DataWrapper(BaseActor actor, double x, double y){
		myActor = actor;
		myX = x;
		myY = y;
	}
	
	
	
	public BaseActor getActor(){		
		return myActor;	
	}
	
	
	public Point2D getPoint(){
		return new Point2D(myX, myY);	
	}

}
