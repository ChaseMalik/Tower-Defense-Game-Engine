package gamePlayer.guiItems.controlDockButtons.sliders;

import gamePlayer.guiItems.controlDockButtons.ControlDockSlider;
import gamePlayer.guiItemsListeners.SpeedSliderListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.io.File;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import utilities.XMLParsing.XMLParser;

public class SpeedSlider extends ControlDockSlider {

	private SpeedSliderListener myListener = GuiConstants.GUI_MANAGER;
	private final double intervals = 5;
	private final double sliderMin = 1;
	private final double sliderMax = 6;
	private final double increment = sliderMax/intervals;

	@Override
	public void initialize (Dimension2D containerSize) {
		super.initialize(containerSize);
		myParser = new XMLParser(new File(GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML"));
		setUpSlider();
		setUpSizing(containerSize);
		myListener.registerSpeedSlider(this);
	}
	
	public void decrementSpeed() {
		DoubleProperty speed = mySlider.getValueProperty();
		speed.set(Math.max(sliderMin, speed.get() - increment));
	}
	
	public void incrementSpeed() {
		DoubleProperty speed = mySlider.getValueProperty();
		speed.set(Math.min(sliderMax, speed.get() + increment));
	}

	private void setUpSlider(){
		mySlider.getValueProperty().addListener(new ChangeListener<Number>(){
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
