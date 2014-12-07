package utilities.GSON;

import javafx.geometry.Point2D;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseTower;


public class DataWrapper {

    private String myName;
    private double myX;
    private double myY;

    public DataWrapper (BaseActor actor) {
        myName = actor.toString();
        myX = actor.getX();
        myY = actor.getY();
    }
    public String getName(){
        return myName;
    }
    public double getX(){
        return myX;
    }
    
    public double getY(){
        return myY;
    }
    
    public boolean equals(BaseTower tower){
        return (tower.toString().equals(myName)) && (tower.getX() == myX) && (tower.getY() == myY);
    }
}
