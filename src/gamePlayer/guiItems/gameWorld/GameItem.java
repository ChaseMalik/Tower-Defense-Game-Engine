package gamePlayer.guiItems.gameWorld;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import utilities.JavaFXutilities.imageView.CenteredImageView;

/**
 * Wraps the GameActor Gui elements passed from the Engine in a group. This
 * allows for interactive game items (see SelectableGameItem) as well as
 * scrollable and zoomable maps
 * 
 * @author brianbolze
 */
public class GameItem {

	protected String myName;
	protected DoubleProperty X, Y, rotation;
	protected ImageView myImageView;
	protected Group myGroup;
	protected Node myNode;

	public GameItem(String name, double X, double Y, ImageView imageView) {
		myName = name;
		myImageView = imageView;
		this.X = new SimpleDoubleProperty(X);
		this.Y = new SimpleDoubleProperty(Y);
		rotation = new SimpleDoubleProperty(0);
		init();
	}

	public Node getNode() {
		return myGroup;
	}

	protected void init() {

		myGroup = new Group();
		myGroup.translateXProperty().bind(X);
		myGroup.translateYProperty().bind(Y);
		myGroup.rotateProperty().bind(rotation);

		CenteredImageView image = new CenteredImageView();
		myImageView.setX(- myImageView.getFitWidth() / 2);
		myImageView.setY(- myImageView.getFitHeight() / 2);

		myGroup.getChildren().add(myImageView);

	}

}
