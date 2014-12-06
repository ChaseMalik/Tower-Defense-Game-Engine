package gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders;

import gameEngine.actors.behaviors.IBehavior;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import utilities.JavaFXutilities.slider.SliderContainer;
import utilities.reflection.Reflection;

/**
 * A class representing JavaFX components used to build a behavior that will be
 * added to an actors list of behaviors. When a new type of behavior (like attack or movement)
 * is added, a new behavior builder class should be constructed title "BehaviorType"Builder.
 * In the behavior xml file (there is one for towers and one for enemies), the different kinds
 * of this type of behavior should be listed. This class will automatically create a comboBox
 * with all of these options. It is your job to create the sliders and other components you need
 * to get all the information to actually make the behavior object. This behavior creation is
 * done in the buildBehavior method which all subclasses must use. To get some good examples, 
 * see MovementBuilder and AttackBuilder.
 * @author Austin Kyker
 *
 */
public class BehaviorBuilder {
    
    private static final int COMBO_BOX_WIDTH = 200;
    
    protected ComboBox<String> myComboBox;
    protected VBox myContainer;
    private SliderContainer mySliderContainer;
    protected SliderInfo mySliderInfo;
    private String myBehaviorType;
    private List<String> myBehaviorOptions;
    
    public BehaviorBuilder(String behaviorType, List<String> behaviorOptions, SliderInfo sliderInfo) {
        myBehaviorType = behaviorType;
        myBehaviorOptions = behaviorOptions;
        mySliderInfo = sliderInfo;
        createCenterDisplay();
        mySliderContainer = new SliderContainer(sliderInfo);
        myContainer.getChildren().add(mySliderContainer);
    }

    public IBehaviorKeyValuePair buildBehavior() {
        String behaviorSelected = myComboBox.getValue();
        double sliderValue = mySliderContainer.getSliderValue();
        String className = "gameEngine.actors.behaviors." + behaviorSelected;
        return new IBehaviorKeyValuePair(myBehaviorType,
                                        (IBehavior) Reflection.createInstance(className, sliderValue));  
    }
    
    public void createCenterDisplay() {
        myContainer = new VBox();
        myContainer.setStyle("-fx-border-width: 1px; " +
                             "-fx-border-color: gray; " +
                             "-fx-padding: 10px; " +
                             "-fx-border-radius: 5px");
        myContainer.setSpacing(20); 
        myContainer.setPrefWidth(COMBO_BOX_WIDTH);
        Label label = new Label(myBehaviorType);
        myComboBox = createComboBox();
        myContainer.getChildren().addAll(label, myComboBox);
    }
    
    private ComboBox<String> createComboBox(){  
        ComboBox<String> CB = new ComboBox<String>();
        CB.setPrefWidth(COMBO_BOX_WIDTH);
        CB.getItems().addAll(myBehaviorOptions);
        return CB;
    }

    public VBox getContainer () {
        return myContainer;
    }

    public void reset () {
        myComboBox.setValue(null);
        mySliderContainer.resetSlider();
    }
}