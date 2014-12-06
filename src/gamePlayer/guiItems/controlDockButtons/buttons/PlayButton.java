package gamePlayer.guiItems.controlDockButtons.buttons;

/**
 * 
 * @author Greg Lyons
 * 
 */

import gamePlayer.Listeners.PlayButtonListener;
import gamePlayer.guiItems.controlDockButtons.ControlDockButton;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.io.File;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import utilities.XMLParsing.XMLParser;

public class PlayButton extends ControlDockButton {
    private String playImage;
    private String pauseImage;
    private PlayButtonListener myListener = GuiConstants.GUI_MANAGER;

    @Override
    public void initialize(Dimension2D containerSize) {
        super.initialize(containerSize);
        String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML";
        myParser = new XMLParser(new File(propertiesPath)); 
        
        playImage = myParser.getValuesFromTag("PlayImage").get(0);
        pauseImage = myParser.getValuesFromTag("PauseImage").get(0);
        
        setUpSizing (containerSize);
        myButton.setOnAction(event -> play());
        setupImageViews(playImage, pauseImage);
        myButton.setGraphic(myImageView);
    }

    private void play(){
        myButton.setOnAction(event -> pause());
        myListener.play();
    }

    private void pause(){
        myButton.setOnAction(event -> play());
        myListener.pause();
    }

    @Override
    public Node getNode () {
        return myButton;
    }
}
