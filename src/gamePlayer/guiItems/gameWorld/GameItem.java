package gamePlayer.guiItems.gameWorld;

import gamePlayer.guiItemsListeners.GameItemListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class GameItem {
	
	protected int myID;
	protected DoubleProperty X, Y, rotation;
	protected ImageView myImageView;
	protected Group myGroup;
	private GameItemListener myListener;
	
	public GameItem(int ID, Point2D loc, ImageView imageView) {
		myID = ID;
		myImageView = imageView;
		X = new SimpleDoubleProperty(loc.getX());
		Y = new SimpleDoubleProperty(loc.getY());
		rotation = new SimpleDoubleProperty(0);
		init();
	}
	
	public int getID() {
		return myID;
	}

	public Node getNode() {
		return myGroup;
	}

	protected void init() {
		
		myGroup = new Group();
		myGroup.translateXProperty().bind(X);
		myGroup.translateYProperty().bind(Y);
		myGroup.rotateProperty().bind(rotation);
				
		myImageView.setLayoutX(-myImageView.getImage().getWidth()/2.);
		myImageView.setLayoutY(-myImageView.getImage().getHeight()/2.);
		
		myGroup.getChildren().add(myImageView);
		
	}

}
