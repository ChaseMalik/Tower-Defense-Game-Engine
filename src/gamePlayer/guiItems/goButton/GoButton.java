package gamePlayer.guiItems.goButton;

import java.io.File;

import utilities.XMLParsing.XMLParser;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import gamePlayer.guiItems.GuiItem;

public class GoButton implements GuiItem {

	private XMLParser myParser;
	private Button myButton;
	private ImageView myImageView;
	private Dimension2D buttonSize;
	private Dimension2D imageSize;
	private int mySpeed;
	
	public static final String playImagePath = "gamePlayer/playerImages/play.png";
	public static final String ffImagePath = "gamePlayer/playerImages/fastforward.png";
	public static final int PAUSED = 0;
	public static final int NORMAL = 1;
	public static final int FAST = 2;
			
	
	public GoButton() {

	}

	@Override
	public void initialize(Dimension2D containerSize) {
		myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML"));
		myImageView = new ImageView();
		myButton = new Button();
		
		setUpSizing(containerSize);
		
		myButton.setOnAction(event -> onClick());
		setImage(playImagePath);
		myButton.setGraphic(myImageView);
		
		mySpeed = 0;
	}

	@Override
	public Node getNode() {
		return myButton;
	}
	
	private void setImage(String path){
		try{
			Image newImage = new Image(path);
			myImageView.setImage(newImage);
		}
		catch(NullPointerException npe){
		    System.out.println("Image error");
		}
	}
	
	private void onClick(){
		switch (mySpeed){
			case PAUSED:
				setImage(ffImagePath);
				mySpeed = NORMAL;
				break;
			case NORMAL:
				setImage(playImagePath);
				mySpeed = FAST;
				break;
			case FAST:
				setImage(ffImagePath);
				mySpeed = NORMAL;
				break;
		}
				
				
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
	
	public void pause(){
		mySpeed = PAUSED;
		onClick();
	}

}
