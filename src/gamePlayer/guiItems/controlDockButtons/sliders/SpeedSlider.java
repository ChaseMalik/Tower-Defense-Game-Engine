package gamePlayer.guiItems.controlDockButtons.sliders;

import gamePlayer.guiItems.controlDockButtons.ControlDockButton;
import gamePlayer.guiItems.controlDockButtons.ControlDockSlider;
import gamePlayer.guiItemsListeners.SpeedSliderListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.io.File;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import utilities.XMLParsing.XMLParser;

public class SpeedSlider extends ControlDockSlider {
//    private String ffImage;
//    private String slowImage;
    private SpeedSliderListener myListener = GuiConstants.GUI_MANAGER;

    @Override
    public void initialize (Dimension2D containerSize) {
        super.initialize(containerSize);
        myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML"));

//        ffImage = myParser.getValuesFromTag("FastForwardImage").get(0);
//        slowImage = myParser.getValuesFromTag("SlowDownImage").get(0);

        setUpSizing (containerSize);
        mySlider.setNodeOrientation(event -> updateSpeed());
//        setupImageViews(ffImage, slowImage);
//        mySlider.setGraphic(myImageView);
    }

//    private void fastForward() {
//        myListener.fastForward();
//        myButton.setOnAction(event-> normalSpeed());
//    }
    
//    private void normalSpeed() {
//        myListener.normalSpeed();
//        myButton.setOnAction(event->fastForward());
//    }
    
//    /**
//     * Method to check the value of the 
//     * fps slider and update the animation speed.
//     */
//    private void updateFPS() {
//        framesPerSecond = (int) Math.round(fpsSlider.getValue());
//        animation.stop();
//        animation.getKeyFrames().remove(frame);
//        frame = makeFrame();
//        animation.getKeyFrames().add(frame);
//        animation.play();
//    }
    
//    fpsSlider = new Slider(1, 10, 1);
//    fpsSlider.setValue(framesPerSecond);
//    fpsSlider.setMajorTickUnit(1);
//    fpsSlider.setSnapToTicks(true);
//    fpsSlider.setMinWidth(BUTTON_WIDTH);
//    fpsSlider.setMaxWidth(BUTTON_WIDTH);
//    fpsSlider.setMajorTickUnit(1.0);
//    fpsSlider.setShowTickMarks(false);
//    fpsSlider.setSnapToTicks(true);
    
//    gridNew.add(fpsSlider, rightSide, numCols + 1);
    
    @Override
    public Node getNode () {
        return mySlider;
    }
}
