package utilities.GSON;

import gameEngine.actors.BaseTower;



public class DataWrapper {
	
	private double myX;
	private double myY;
	private BaseTower myActor;

	public DataWrapper(BaseTower actor, double x, double y){
		myActor = actor;
		myX = x;
		myY = y;
	}
	

}
