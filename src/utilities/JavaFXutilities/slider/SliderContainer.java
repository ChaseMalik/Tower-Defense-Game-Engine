package utilities.JavaFXutilities.slider;

import gameAuthoring.mainclasses.Constants;
import gameAuthoring.scenes.actorBuildingScenes.behaviorBuilders.SliderInfo;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utilities.multilanguage.MultiLanguageUtility;


/**
 * Purpose of this class is to allow front-end components to easily be able
 * to build labeled sliders.
 * 
 * @author Austin Kyker
 *
 */
public class SliderContainer extends VBox {

    private static final int SLIDER_WIDTH = 120;
    private Slider mySlider;
    private SliderInfo mySliderInfo;
    private Label myValueLabel;

    public SliderContainer (String name, double min, double max) {
        super(Constants.SM_PADDING);
        mySliderInfo = new SliderInfo(name, min, max);
        setupSlider();
        HBox nameAndValue = new HBox(Constants.SM_PADDING);
        myValueLabel = new Label(Double.toString(mySlider.getValue()));
        nameAndValue.getChildren().addAll(getNameLabel(name), myValueLabel);
        this.getChildren().addAll(nameAndValue, mySlider);
    }

    public SliderContainer (SliderInfo info) {
        super(Constants.SM_PADDING);
        mySliderInfo = info;
        setupSlider();
        HBox sliderAndValue = new HBox(Constants.SM_PADDING);
        myValueLabel = new Label(Double.toString(mySlider.getValue()));
        sliderAndValue.getChildren().addAll(mySlider, myValueLabel);
        this.getChildren().addAll(getNameLabel(mySliderInfo.getMyInfo()), sliderAndValue);
    }

    private void setupSlider () {
        mySlider = new Slider();
        mySlider.setPrefWidth(SLIDER_WIDTH);
        mySlider.setMax(mySliderInfo.getMyMax());
        mySlider.setMin(mySliderInfo.getMyMin());
        mySlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed (ObservableValue<? extends Number> ov,
                                 Number old_val, Number new_val) {
                myValueLabel.setText(Double.toString(Math.round(mySlider.getValue() * 10) / 10.0));
            }
        });
    }

    private Label getNameLabel (String name) {
        Label label = new Label();
        label.textProperty().bind(MultiLanguageUtility.getInstance().getStringProperty(name));
        return label;
    }

    public void resetSlider () {
        mySlider.setValue(mySliderInfo.getMyMin());
    }

    public double getSliderValue () {
        return mySlider.getValue();
    }

    public DoubleProperty getValueProperty () {
        return mySlider.valueProperty();
    }
}
