package gamePlayer.guiItems.controlDockButtons;

import javafx.geometry.Dimension2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utilities.XMLParsing.XMLParser;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItemsListeners.PlayButtonListener;
import gamePlayer.mainClasses.ExceptionHandling.ExceptionHandler;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

public abstract class ControlDockButton implements GuiItem {
    protected XMLParser myParser;
    protected ImageView myImageView;
    protected Button myButton;

    protected Dimension2D buttonSize;
    protected Dimension2D imageSize;

    @Override
    public void initialize(Dimension2D containerSize) {
        myImageView = new ImageView();
        myButton = new Button();
        myButton.getStyleClass().add("ControlDockButton");
    }
    
    protected void setImageviewImage(String path){
        try {
            Image newImage = new Image(path);
            myImageView.setImage(newImage);
        }
        catch (NullPointerException npe) {
            ExceptionHandler.getInstance().handle(npe);
        }
    }
    
    protected void setUpSizing(Dimension2D containerSize){
        Dimension2D buttonRatio = myParser.getDimension("SizeRatio");
        Dimension2D imageRatio = myParser.getDimension("ImageRatio");

        buttonSize = new Dimension2D(buttonRatio.getWidth()*containerSize.getWidth(), 
                                     buttonRatio.getHeight()*containerSize.getHeight());
        imageSize = new Dimension2D(imageRatio.getWidth()*buttonSize.getWidth(),
                                    imageRatio.getHeight()*buttonSize.getHeight());

        myButton.setMinSize(buttonSize.getWidth(), buttonSize.getHeight());
        myButton.setPrefSize(buttonSize.getWidth(), buttonSize.getHeight());
        myImageView.setFitHeight(imageSize.getHeight());
        myImageView.setFitWidth(imageSize.getWidth());
    }
    
}
