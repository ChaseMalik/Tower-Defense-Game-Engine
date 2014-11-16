package gameEngine.actors.behaviors;

import javafx.scene.Group;
import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.BaseActor;

public class LinearMovementBehavior extends BaseMoveBehavior {

    public LinearMovementBehavior (BackendRoute route, double speed) {
        super(route, speed);
    }

    @Override
    public void execute (BaseActor actor) {
        
    }

}
