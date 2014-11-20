package gamePlayer.guiItems.controlDockButtons.buttons;

import gamePlayer.guiItems.controlDockButtons.ControlDockButton;
import gamePlayer.guiItemsListeners.PlayButtonListener;
import gamePlayer.guiItemsListeners.SpeedButtonListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import java.io.File;
import utilities.XMLParsing.XMLParser;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class SpeedButton extends ControlDockButton {
    private String ffImage;
    private String slowImage;
    private SpeedButtonListener myListener = GuiConstants.GUI_MANAGER;

    @Override
    public void initialize (Dimension2D containerSize) {
        myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML"));
        myImageView = new ImageView();
        myButton = new Button();

        ffImage = myParser.getValuesFromTag("FastForwardImage").get(0);
        slowImage = myParser.getValuesFromTag("SlowDownImage").get(0);

        setUpSizing (containerSize);

        myButton.setOnAction(event -> fastForward());
        setImageviewImage(ffImage);
        myButton.setGraphic(myImageView);
    }

    private void fastForward() {
        setImageviewImage(slowImage);
        myListener.fastForward();
        myButton.setOnAction(event-> normalSpeed());
    }
    
    private void normalSpeed() {
        setImageviewImage(ffImage);
        myListener.normalSpeed();
        myButton.setOnAction(event->fastForward());
    }
    
    @Override
    public Node getNode () {
        return myButton;
    }
}
