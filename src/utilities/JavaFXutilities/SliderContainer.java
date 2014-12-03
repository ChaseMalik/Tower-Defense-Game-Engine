package utilities.JavaFXutilities;

import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.SliderInfo;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

/**
 * Purpose of this class is to allow front-end components to easily be able
 * to build labeled sliders.
 * @author Austin Kyker
 *
 */
public class SliderContainer extends VBox {
    
    private static final int NUM_TICKS = 5;
    private Slider mySlider;
    private SliderInfo mySliderInfo;
    
    public SliderContainer(String name, double min, double max) {
        this(new SliderInfo(name, min, max));
    }
    
    public SliderContainer(SliderInfo info) {
        super(10);
        mySliderInfo = info;
        mySlider = new Slider();
        resetSlider();
        this.getChildren().addAll(new Label(info.getMyInfo()), mySlider);
    }
    
    public void resetSlider() {
        mySlider.setMax(mySliderInfo.getMyMax());
        mySlider.setMin(mySliderInfo.getMyMin());
        mySlider.setMajorTickUnit((mySliderInfo.getMyMax() - mySliderInfo.getMyMin())/NUM_TICKS);
        mySlider.setShowTickLabels(true);
        mySlider.setShowTickMarks(true);
        mySlider.setMinorTickCount(0);
        mySlider.snapToTicksProperty().set(true);
    }

    public double getSliderValue () {
        return mySlider.getValue();
    }
}
