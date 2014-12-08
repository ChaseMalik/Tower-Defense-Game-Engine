package gamePlayer.guiItems.controlDockButtons;

import gamePlayer.guiItems.GuiItem;
//import gamePlayer.mainClasses.ExceptionHandling.ExceptionHandler;
//import javafx.beans.binding.Bindings;
import javafx.geometry.Dimension2D;
import javafx.scene.control.Slider;
import utilities.JavaFXutilities.ratioSizing.RatiosToDim;
import utilities.JavaFXutilities.slider.SliderContainer;
//import javafx.scene.control.ToggleButton;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import utilities.XMLParsing.XMLParser;

public abstract class ControlDockSlider implements GuiItem {
    protected XMLParser myParser;
    protected SliderContainer mySlider;
    
    public static final String LABEL = "GameSpeed";
    
    @Override
    public void initialize(Dimension2D containerSize) {
    	mySlider = new SliderContainer("LABEL",1.0, 6.0);
    }

    protected void setUpSizing(Dimension2D containerSize) {
        Dimension2D sliderRatio = myParser.getDimension("SizeRatio");
        Dimension2D sliderSize = RatiosToDim.convert(containerSize, sliderRatio);
    	mySlider.setMaxSize(sliderSize.getWidth(), sliderSize.getHeight());
    }
}
