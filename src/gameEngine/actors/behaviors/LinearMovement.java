package gameEngine.actors.behaviors;

import java.util.List;
import javafx.geometry.Point2D;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;


public class LinearMovement extends BaseMovementBehavior {
    int myIndex = 0;

    public LinearMovement (List<BackendRoute> routeOptions, double speed) {
        super(routeOptions, speed);
    }

    @Override
    public void execute (BaseActor actor) {
        if (myIndex == 0) {
            actor.setVisible(true);
            actor.setX(myRoute.get(0).getPoint().getX());
            actor.setY(myRoute.get(0).getPoint().getY());
            myIndex++;
            return;
        }
        if (myIndex == myRoute.size()){
            //TODO handle this
            return;
        }
        Point2D position = new Point2D(actor.getX(),actor.getY());
        
        Point2D destination = myRoute.get(myIndex).getPoint();
        
        double speed = mySpeed;
        
        while(speed > destination.distance(position)){
            speed = destination.distance(position);
            myIndex++;
            if(myIndex == myRoute.size()){
                // TODO Handle this
                return;
            }
            position = new Point2D(destination.getX(),destination.getY());
            destination = myRoute.get(myIndex).getPoint();
        }
        
        actor.setVisible(myRoute.get(myIndex).isVisible());
        Point2D vector = destination.subtract(position).normalize().multiply(mySpeed);
        Point2D answer = position.add(vector);
        actor.setX(answer.getX());
        actor.setY(answer.getY());
        
    }

    @Override
    public IBehavior copy () {
        return new LinearMovement(myOptions, mySpeed);
    }

}
