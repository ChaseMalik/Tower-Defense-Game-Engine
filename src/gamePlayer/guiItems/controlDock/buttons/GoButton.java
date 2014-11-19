package gamePlayer.guiItems.controlDock.buttons;

/**
 * 
 * @author Greg Lyons
 * 
 */

import gamePlayer.guiItems.controlDock.ControlDockButton;
import gamePlayer.guiItemsListeners.ControlDockListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import java.io.File;
import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utilities.XMLParsing.XMLParser;

public class GoButton extends ControlDockButton{
	private XMLParser myParser;
	private ImageView myImageView;
	private Dimension2D buttonSize;
	private Dimension2D imageSize;
	
	private ControlDockListener myListener = GuiConstants.GUI_MANAGER;
	
	private String playImage;
	private String ffImage;

	@Override
	public void initialize(Dimension2D containerSize) {
		myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML"));
		myImageView = new ImageView();
		
		List<String> images = myParser.getValuesFromTag("Images");
		playImage = images.get(0);
		ffImage = images.get(1);
		
		setUpSizing(containerSize);
		
		this.setOnAction(event -> play());
		setImage(playImage);
		this.setGraphic(myImageView);
	}
	
	private void setImage(String path){
		try{
			Image newImage = new Image(path);
			myImageView.setImage(newImage);
		}
		catch(NullPointerException npe){
		}
	}
	
	private void play(){
		setImage(ffImage);
		this.setOnAction(event -> fastForward());
		myListener.play();
	}
	
	private void fastForward(){
		setImage(playImage);
		this.setOnAction(event -> play());
		myListener.fastforward();
	}
	
	public void pause(){
		setImage(playImage);
		this.setOnAction(event -> play());
		myListener.pause();
	}
	
	private void setUpSizing(Dimension2D containerSize){
		Dimension2D buttonRatio = myParser.getDimension("SizeRatio");
		Dimension2D imageRatio = myParser.getDimension("ImageRatio");

		buttonSize = new Dimension2D(buttonRatio.getWidth()*containerSize.getWidth(), 
								buttonRatio.getHeight()*containerSize.getHeight());
		imageSize = new Dimension2D(imageRatio.getWidth()*buttonSize.getWidth(),
									imageRatio.getHeight()*buttonSize.getHeight());

		this.setPrefSize(buttonSize.getWidth(), buttonSize.getHeight());
		myImageView.setFitHeight(imageSize.getHeight());
		myImageView.setFitWidth(imageSize.getWidth());
	}
}
