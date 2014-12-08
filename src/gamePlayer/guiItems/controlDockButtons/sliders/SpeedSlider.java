package gamePlayer.guiItems.controlDockButtons.sliders;

import gamePlayer.guiItems.controlDockButtons.ControlDockSlider;
import gamePlayer.guiItemsListeners.SpeedSliderListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import utilities.XMLParsing.XMLParser;

public class SpeedSlider extends ControlDockSlider {

	private SpeedSliderListener myListener = GuiConstants.GUI_MANAGER;

	@Override
	public void initialize (Dimension2D containerSize) {
		super.initialize(containerSize);
		myParser = new XMLParser(new File(GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML"));
		setUpSlider();
		setUpSizing(containerSize);
		myListener.registerSpeedSlider(this);
	}
	
	public void decrementSpeed() {
		System.out.println("decrement!");
		double val = mySlider.getValue();
		if (val-1 >= mySlider.getMin()) {
			mySlider.setValue(val - 1);
		}
	}
	
	public void incrementSpeed() {
		double val = mySlider.getValue();
		if (val+1 <= mySlider.getMax()) {
			mySlider.setValue(val + 1);
		}
	}

	private void setUpSlider(){

		mySlider.setValue(1.0);
		mySlider.setMin(0.5);
		mySlider.setMax(5);
		mySlider.setMajorTickUnit(0.5);
		mySlider.setShowTickMarks(true);
		mySlider.setSnapToTicks(true);

		mySlider.valueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
				myListener.changeSpeed((double) o.getValue());
			}
		});
	}

	@Override
	public Node getNode () {
		return mySlider;
	}
}
