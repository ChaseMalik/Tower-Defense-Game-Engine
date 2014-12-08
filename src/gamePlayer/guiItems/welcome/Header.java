package gamePlayer.guiItems.welcome;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.io.File;

import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import utilities.XMLParsing.XMLParser;

public class Header implements GuiItem {

	private XMLParser myParser;
	private HBox myRoot;
	private ImageView myImage;
	private Dimension2D mySize;
	
	@Override
	public void initialize(Dimension2D containerSize) {
		mySize = containerSize;
		String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML";
        myParser = new XMLParser(new File(propertiesPath)); 
        
        setupPane();
		setupImage();
	}

	@Override
	public Node getNode() {
		return myRoot;
	}
	
	private void setupPane() {
		myRoot = new HBox();
		myRoot.setPrefSize(mySize.getWidth(), mySize.getHeight());
		myRoot.setAlignment(Pos.CENTER);
		myRoot.getStyleClass().add("Header");
	}
	
	private void setupImage() {
		String imagePath = myParser.getValuesFromTag("Image").get(0);
		double imageScale = myParser.getDoubleValuesFromTag("ImageScale").get(0);
		double size = imageScale*mySize.getHeight();
		myImage = new ImageView();
		Image image = new Image(imagePath, 0, size, true, true);
		if (image != null)
			myImage.setImage(image);
		myRoot.getChildren().add(myImage);
	}

}
