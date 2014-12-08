package gamePlayer.guiItems.controlDockButtons;

import gamePlayer.guiItems.GuiItem;
//import gamePlayer.mainClasses.ExceptionHandling.ExceptionHandler;
//import javafx.beans.binding.Bindings;
import javafx.geometry.Dimension2D;
import javafx.scene.control.Slider;
//import javafx.scene.control.ToggleButton;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import utilities.XMLParsing.XMLParser;

public abstract class ControlDockSlider implements GuiItem {
    protected XMLParser myParser;
    protected Slider mySlider;

    protected Dimension2D sliderSize;

    @Override
    public void initialize(Dimension2D containerSize) {
    	mySlider = new Slider();
    }

    protected void setUpSizing(Dimension2D containerSize) {
        Dimension2D sliderRatio = myParser.getDimension("SizeRatio");

        sliderSize = new Dimension2D(sliderRatio.getWidth()
                * containerSize.getWidth(), sliderRatio.getHeight()
                * containerSize.getHeight());
       // mySlider.setMinSize(sliderSize.getWidth(), sliderSize.getHeight());
    	mySlider.setMaxSize(sliderSize.getWidth(), sliderSize.getHeight());
        //mySlider.setPrefSize(sliderSize.getWidth(), sliderSize.getHeight());
       // mySlider.setMaxHeight(sliderSize.getHeight());
       // mySlider.setMaxWidth(sliderSize.getWidth());
    }
}
