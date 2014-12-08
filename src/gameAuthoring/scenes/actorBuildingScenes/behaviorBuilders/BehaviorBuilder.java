package gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders;

import gameAuthoring.mainclasses.Constants;
import gameEngine.actors.behaviors.IBehavior;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import utilities.JavaFXutilities.slider.SliderContainer;
import utilities.multilanguage.MultiLanguageUtility;
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

    private static final double COMBO_BOX_WIDTH = 160;

    protected ComboBox<String> myComboBox;
    protected VBox myContainer;
    private List<SliderContainer> mySliderContainers;
    protected SliderInfo mySliderInfo;
    private String myBehaviorType;
    private List<String> myBehaviorOptions;

    public BehaviorBuilder(String behaviorType, List<String> behaviorOptions, 
                           List<SliderInfo> sliderInfoObjects) {
        myBehaviorType = behaviorType;
        myBehaviorOptions = behaviorOptions;
        createCenterDisplay();
        for(SliderInfo info:sliderInfoObjects) {
            mySliderContainers.add(new SliderContainer(info));
        }
        myContainer.getChildren().addAll(mySliderContainers);
    }

    public IBehaviorKeyValuePair buildBehavior() {
        String behaviorSelected = myComboBox.getValue();
        List<Double> sliderValues = mySliderContainers.stream()
                .map(slider->slider.getSliderValue())
                .collect(Collectors.toList());
        String className = "gameEngine.actors.behaviors." + behaviorSelected;
        return new IBehaviorKeyValuePair(myBehaviorType,
                                         (IBehavior) Reflection.createInstance(className, sliderValues));  
    }

    public void createCenterDisplay() {
        myContainer = new VBox();
        myContainer.setStyle("-fx-border-width: 1px; -fx-border-color: gray; " +
                             "-fx-padding: 10px; -fx-border-radius: 5px");
        myContainer.setSpacing(Constants.LG_PADDING); 
        Label label = new Label();
        label.textProperty().bind(MultiLanguageUtility.getInstance()
                                  .getStringProperty(myBehaviorType));
        myComboBox = createComboBox();
        myContainer.getChildren().addAll(label, myComboBox);
    }

    private ComboBox<String> createComboBox(){  
        ComboBox<String> CB = new ComboBox<String>();
        CB.setMinWidth(COMBO_BOX_WIDTH);
        CB.setMaxWidth(COMBO_BOX_WIDTH);
        CB.getItems().addAll(myBehaviorOptions);
        return CB;
    }

    public VBox getContainer () {
        return myContainer;
    }

    public void reset () {
        myComboBox.setValue(null);
        for(SliderContainer container:mySliderContainers) {
            container.resetSlider();
        }
    }
}