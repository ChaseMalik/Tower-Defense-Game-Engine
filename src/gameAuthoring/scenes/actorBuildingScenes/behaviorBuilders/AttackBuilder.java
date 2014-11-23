package gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import gameEngine.actors.behaviors.IBehavior;
import gameEngine.actors.behaviors.NullBehavior;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import utilities.reflection.Reflection;

/**
 * Defines the components necessary to create a attack behavior object for the actor.
 * @author Austin Kyker
 *
 */
public class AttackBuilder extends BehaviorBuilder {

    private static final String ATTACK = "attack";

    private Slider myAttackSpeedSlider;

    public AttackBuilder(List<BackendRoute> routes, List<String> attackOptions) {
        super(routes, attackOptions);
        createCenterDisplay(ATTACK);
        setUpSlider();
        myContainer.getChildren().add(myAttackSpeedSlider);
        myComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                handleSelection();
            }    
        });
    }

    private void handleSelection () {
        myAttackSpeedSlider.setDisable(isNoAttackSelected());
    }

    private boolean isNoAttackSelected () {
        return "No attack".equalsIgnoreCase(myComboBox.getValue());
    }

    private void setUpSlider () {
        myAttackSpeedSlider = new Slider();
        setSliderProperties(myAttackSpeedSlider);
    }

    @Override
    public IBehaviorKeyValuePair buildBehavior () {
        if(isNoAttackSelected()) {
            return new IBehaviorKeyValuePair(ATTACK, new NullBehavior());
        }
        String typeOfAttack = myComboBox.getValue();
        int speed = (int) myAttackSpeedSlider.getValue();
        String className = "gameEngine.actors.behaviors." + typeOfAttack;
        return new IBehaviorKeyValuePair(ATTACK, 
                                         (IBehavior) Reflection.createInstance(className, speed));
    }

    @Override
    public void reset() {
        super.reset();
        setSliderProperties(myAttackSpeedSlider);
    }
}