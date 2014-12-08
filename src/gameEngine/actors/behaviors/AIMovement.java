package gameEngine.actors.behaviors;

import gameEngine.GridPathFinder;
import gameEngine.InformationInterface;
import gameEngine.actors.BaseActor;
import gameEngine.actors.BaseEnemy;
import gameEngine.actors.InfoObject;
import java.util.List;
import javafx.geometry.Point2D;


public class AIMovement extends BaseMovementBehavior {
    private transient GridPathFinder myPathFinder;
    private transient List<Point2D> myAIRoute;
    private int myIndex;
    public AIMovement (List<Double> list) {
        super(list);
        myPathFinder = new GridPathFinder();
        myIndex=-1;
    }

    @Override
    public void execute (BaseActor actor) {
        // TODO Auto-generated method stub
        InformationInterface info = actor.getInfoObject();
        BaseEnemy enemy = (BaseEnemy)actor;
        if(myIndex == -1) {
        	move(enemy, enemy.getStart().getPoint());
        	myAIRoute =myPathFinder.getPath(enemy, info.getReferencePane(), info.getExistingTowerTiles());
            myIndex=0;
        }
        else if (info.checkNewPath()) {
            myAIRoute =myPathFinder.getPath(enemy, info.getReferencePane(), info.getExistingTowerTiles());
            myIndex=0;
        }
        if(myAIRoute==null){
            //System.out.println("no path");
            return;
        }
        Point2D current = new Point2D(actor.getX(), actor.getY());
        Point2D destination = myAIRoute.get(myIndex);
        double distance = mySpeed;

        while (distance > destination.distance(current)) {
            myIndex++;
            if (myIndex == myAIRoute.size()) {
                actor.died();
                return;
            }
            distance -= destination.distance(current);
            current = new Point2D(destination.getX(), destination.getY());
            destination = myAIRoute.get(myIndex);
        }
        myRemainingDistance-=mySpeed;
        Point2D vector = destination.subtract(current).normalize().multiply(mySpeed);
        Point2D answer = current.add(vector);
        move(actor, answer);
    }

    @Override
    public IBehavior copy () {
        // TODO Auto-generated method stub
        return new AIMovement(myList);
    }

}
