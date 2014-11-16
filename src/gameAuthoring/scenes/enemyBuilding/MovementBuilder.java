package gameAuthoring.scenes.enemyBuilding;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.behaviors.IBehavior;
import java.util.List;
import java.util.Random;
import javafx.scene.control.Slider;
import utilities.reflection.Reflection;

public class MovementBuilder extends BehaviorBuilder {

    private static final String MOVEMENT = "movement";
    private static final Random random = new Random();
    
    private Slider mySpeedSlider;
    private List<BackendRoute> myRoutes;

    public MovementBuilder(List<BackendRoute> routes) {
        myRoutes = routes;
        createCenterDisplay(MOVEMENT);
        setUpSlider();
        myContainer.getChildren().addAll(mySpeedSlider);
    }

    private void setUpSlider () {
        mySpeedSlider = new Slider();
        setSliderProperties(mySpeedSlider);
    }
    
    private BackendRoute fetchRandomBackendRoute() {
        return myRoutes.get(random.nextInt(myRoutes.size()));
    }

    @Override
    public IBehavior buildBehavior () {
        String typeOfMovement = myComboBox.getValue();
        double speed = mySpeedSlider.getValue();
        String className = "gameEngine.actors.behaviors." + typeOfMovement;
        return (IBehavior) Reflection.createInstance(className, fetchRandomBackendRoute(), speed);   
    }
}
