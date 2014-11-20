package gamePlayer.guiItems.playButton;

/**
 * 
 * @author Greg Lyons
 * 
 */

import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItemsListeners.PlayButtonListener;
import gamePlayer.mainClasses.ExceptionHandling.ExceptionHandler;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import java.io.File;
import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utilities.XMLParsing.XMLParser;

public class PlayButton implements GuiItem {
    private XMLParser myParser;
    private ImageView myImageView;
    private Button myButton;

    private Dimension2D buttonSize;
    private Dimension2D imageSize;

    private PlayButtonListener myListener = GuiConstants.GUI_MANAGER;

    private String playImage;
    private String pauseImage;

    @Override
    public void initialize(Dimension2D containerSize) {
        myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML"));
        myImageView = new ImageView();
        myButton = new Button();

        playImage = myParser.getValuesFromTag("PlayImage").get(0);
        pauseImage = myParser.getValuesFromTag("PauseImage").get(0);

        setUpSizing(containerSize);

        myButton.setOnAction(event -> play());
        setImageviewImage(playImage);
        myButton.setGraphic(myImageView);
    }

    private void setImageviewImage(String path){
        try {
            Image newImage = new Image(path);
            myImageView.setImage(newImage);
        }
        catch (NullPointerException npe) {
            ExceptionHandler.getInstance().handle(npe);
        }
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

    private void setUpSizing(Dimension2D containerSize){
        Dimension2D buttonRatio = myParser.getDimension("SizeRatio");
        Dimension2D imageRatio = myParser.getDimension("ImageRatio");

        buttonSize = new Dimension2D(buttonRatio.getWidth()*containerSize.getWidth(), 
                                     buttonRatio.getHeight()*containerSize.getHeight());
        imageSize = new Dimension2D(imageRatio.getWidth()*buttonSize.getWidth(),
                                    imageRatio.getHeight()*buttonSize.getHeight());

        myButton.setPrefSize(buttonSize.getWidth(), buttonSize.getHeight());
        myImageView.setFitHeight(imageSize.getHeight());
        myImageView.setFitWidth(imageSize.getWidth());
    }

    @Override
    public Node getNode () {
        return myButton;
    }
}
