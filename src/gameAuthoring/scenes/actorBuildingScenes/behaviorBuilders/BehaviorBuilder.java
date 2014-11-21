package gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders;

import gameAuthoring.scenes.pathBuilding.pathComponents.routeToPointTranslation.BackendRoute;
import java.util.List;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

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
public abstract class BehaviorBuilder {
    
    private static final int COMBO_BOX_WIDTH = 200;
    private static final int SLIDER_MIN = 1;
    private static final int SLIDER_MAX = 5;
    private static final int SLIDER_TICK_UNIT = 1;
    
    protected ComboBox<String> myComboBox;
    protected VBox myContainer;
    protected List<BackendRoute> myRoutes;
    protected List<String> myBehaviorOptions;
    
    public BehaviorBuilder(List<BackendRoute> routes, List<String> behaviorOptions) {
        myRoutes = routes;
        myBehaviorOptions = behaviorOptions;
    }
    
    public abstract IBehaviorKeyValuePair buildBehavior();
    
    public void createCenterDisplay(String title) {
        myContainer = new VBox();
        myContainer.setSpacing(20); 
        myContainer.setPrefWidth(COMBO_BOX_WIDTH);
        Label label = new Label(title);
        myComboBox = createComboBox(title);
        myContainer.getChildren().addAll(label, myComboBox);
    }
    
    protected void setSliderProperties (Slider slider) {
        slider.setMin(SLIDER_MIN);
        slider.setMax(SLIDER_MAX);
        slider.setValue(SLIDER_MIN);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(SLIDER_TICK_UNIT);
        slider.setMinorTickCount(0);
        slider.snapToTicksProperty().set(true);
    }
    
    private ComboBox<String> createComboBox(String comboBoxTitle){  
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
    }
}