package gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.behaviors.IBehavior;
import java.util.List;
import javafx.scene.control.Slider;
import utilities.reflection.Reflection;

/**
 * Defines the components necessary to create a movement behavior object for the actor.
 * @author Austin Kyker
 *
 */
public class MovementBuilder extends BehaviorBuilder {

    private static final String MOVEMENT = "movement";
    
    private Slider mySpeedSlider;

    public MovementBuilder(List<BackendRoute> routes, List<String> movementOptions) {
        super(routes, movementOptions);
        createCenterDisplay(MOVEMENT);
        setUpSlider();
        myContainer.getChildren().addAll(mySpeedSlider);
    }

    private void setUpSlider () {
        mySpeedSlider = new Slider();
        setSliderProperties(mySpeedSlider);
    }

    @Override
    public IBehaviorKeyValuePair buildBehavior () {
        String typeOfMovement = myComboBox.getValue();
        double speed = mySpeedSlider.getValue();
        String className = "gameEngine.actors.behaviors." + typeOfMovement;
        return new IBehaviorKeyValuePair(MOVEMENT, (IBehavior) Reflection.createInstance(className, myRoutes, speed));   
    }
    
    @Override
    public void reset() {
        super.reset();
        setSliderProperties(mySpeedSlider);
    }
}
