package gameEngine.actors.behaviors;

import javafx.scene.Group;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;

public class LinearMovement extends BaseMovementBehavior {
    int myIndex = 0;
    public LinearMovement (BackendRoute route, double speed) {
        super(route, speed);
    }

    @Override
    public void execute (BaseActor actor) {
        if(myIndex == 0){
            actor.setVisible(true);
            actor.setX(myRoute.get(0).getPoint().getX());
            actor.setY(myRoute.get(0).getPoint().getY());
        }
        
        double x = actor.getX();
        double y = actor.getY();
    }

}