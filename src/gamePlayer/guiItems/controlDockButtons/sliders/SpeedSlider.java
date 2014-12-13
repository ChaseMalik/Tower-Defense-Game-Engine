package gamePlayer.guiItems.controlDockButtons.sliders;

import gamePlayer.guiItems.controlDockButtons.ControlDockSlider;
import gamePlayer.guiItemsListeners.SpeedSliderListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Dimension2D;
import utilities.XMLParsing.XMLParser;

public class SpeedSlider extends ControlDockSlider {

	private SpeedSliderListener myListener = GuiConstants.GUI_MANAGER;
	private final double numIncrements = 5;
	private double increment;

	@Override
	public void initialize (Dimension2D containerSize) {
		myParser = new XMLParser(new File(GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML"));
		super.initialize(containerSize);
		setUpSlider();
		setUpSizing(containerSize);
		myListener.registerSpeedSlider(this);
		increment = mySlider.getMax()/numIncrements;
	}
	
	public void decrementSpeed() {
		double val = mySlider.getValue();
		if (val-increment >= mySlider.getMin()) {
			mySlider.setValue(val - increment);
		}
	}
	
	public void incrementSpeed() {
		double val = mySlider.getValue();
		if (val+increment <= mySlider.getMax()) {
			mySlider.setValue(val + increment);
		}
	}

	private void setUpSlider(){

		mySlider.valueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> o, Number oldValue, Number newValue) {
				myListener.changeSpeed((double) o.getValue());
			}
		});
	}
}
