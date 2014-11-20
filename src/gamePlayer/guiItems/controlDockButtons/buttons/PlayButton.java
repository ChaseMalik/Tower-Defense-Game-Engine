package gamePlayer.guiItems.controlDockButtons.buttons;

/**
 * 
 * @author Greg Lyons
 * 
 */

import gamePlayer.guiItems.controlDockButtons.ControlDockButton;
import gamePlayer.guiItemsListeners.PlayButtonListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import java.io.File;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import utilities.XMLParsing.XMLParser;

public class PlayButton extends ControlDockButton {
    private String playImage;
    private String pauseImage;
    private PlayButtonListener myListener = GuiConstants.GUI_MANAGER;

    @Override
    public void initialize(Dimension2D containerSize) {
        myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML"));
        myImageView = new ImageView();
        myButton = new Button();

        playImage = myParser.getValuesFromTag("PlayImage").get(0);
        pauseImage = myParser.getValuesFromTag("PauseImage").get(0);

        setUpSizing (containerSize);

        myButton.setOnAction(event -> play());
        setImageviewImage(playImage);
        myButton.setGraphic(myImageView);
    }

    private void play(){
        setImageviewImage(pauseImage);
        myButton.setOnAction(event -> pause());
        myListener.play();
    }

    private void pause(){
        setImageviewImage(playImage);
        myButton.setOnAction(event -> play());
        myListener.pause();
    }

    @Override
    public Node getNode () {
        return myButton;
    }
}
