package gameEngine.actors.behaviors;

import javafx.scene.Group;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;

public class LinearMovementBehavior extends BaseMoveBehavior {
    int myIndex = 0;
    public LinearMovementBehavior (BackendRoute route, double speed) {
        super(route, speed);
    }

    @Override
    public void execute (BaseActor actor) {
        if(myIndex == 0){
            actor.setVisible(true);
            actor.setX(myRoute.get);
        }
        double x = actor.getX();
        double y = actor.getY();
        myRoute.
    }

}