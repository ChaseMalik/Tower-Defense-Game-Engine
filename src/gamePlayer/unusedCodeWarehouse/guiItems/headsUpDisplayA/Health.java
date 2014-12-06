package gamePlayer.unusedCodeWarehouse.guiItems.headsUpDisplayA;

import java.io.File;

import utilities.XMLParsing.XMLParser;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import gamePlayer.Listeners.HealthListener;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

public class Health extends VBox implements GuiItem {

	private XMLParser myParser;
	private Dimension2D mySize;
	private Dimension2D imageSize;
	private ImageView myHeart;
	private ProgressBar myHealthBar;
	
	//private HealthListener myListener = GuiConstants.GUI_MANAGER;

	private DoubleProperty healthRemaining;

	public Health() {

	}

	@Override
	public void initialize(Dimension2D containerSize) {
		myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML")); 

		Dimension2D sizeRatio = myParser.getDimension("SizeRatio");
		mySize = new Dimension2D(containerSize.getWidth()*sizeRatio.getWidth(),
				containerSize.getHeight()*sizeRatio.getHeight());
		this.setPrefSize(mySize.getWidth(),mySize.getHeight());

		makeHeartImage();
		makeHealthBar();
		this.getChildren().addAll(myHeart, myHealthBar);
	}

	@Override
	public Node getNode() {
		return this;
	}

	private void makeHeartImage() {
		myHeart = new ImageView();
		try{
			Image newImage = new Image(myParser.getValuesFromTag("Image").get(0));
			myHeart.setImage(newImage);
		}
		catch(NullPointerException npe){
		}
		Dimension2D imageRatio = myParser.getDimension("ImageRatio");
		imageSize = new Dimension2D(imageRatio.getWidth()*mySize.getWidth(),
				imageRatio.getHeight()*mySize.getHeight());

		myHeart.setFitHeight(imageSize.getHeight());
		myHeart.setFitWidth(imageSize.getWidth());
	}


private void makeHealthBar() {
	myHealthBar = new ProgressBar();
	healthRemaining = myHealthBar.progressProperty();
	myHealthBar.setProgress(1);
	myHealthBar.setPrefWidth(imageSize.getWidth());
	//myListener.bindHealth(healthRemaining);
}

}
