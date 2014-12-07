package utilities.GSON;

import gameEngine.actors.BaseActor;

public class DataWrapper {

	private String myName;
	private double myX;
	private double myY;

	public DataWrapper (BaseActor actor) {
		myName = actor.toString();
		myX = actor.getX();
		myY = actor.getY();
	}

	public String getName () {
		return myName;
	}

	public double getX () {
		return myX;
	}

	public double getY () {
		return myY;
	}

	@Override
	public boolean equals (Object obj) {
		if (obj instanceof BaseActor) {
			BaseActor actor = (BaseActor)obj;
			return (actor.toString().equals(myName)) && (actor.getX() == myX)
					&& (actor.getY() == myY);
		} 
		else if (obj instanceof DataWrapper) {
			DataWrapper w = (DataWrapper)obj;
			return (w.getName().equals(this.getName()) && (w.getX() == myX) && w
					.getY() == myY);
		}
		return false;
	}
}
