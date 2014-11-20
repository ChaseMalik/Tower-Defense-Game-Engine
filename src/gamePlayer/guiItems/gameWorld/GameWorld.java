package gamePlayer.guiItems.gameWorld;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItemsListeners.GameWorldListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


/**
 * 
 * @author Greg Lyons
 * 
 * GameWorld serves as the background in which the animation and interaction of all of the actors takes place.
 */


public class GameWorld implements GuiItem {
	
	Pane myMap;
	GameWorldListener myListener;

	public GameWorld() {
		myMap = new Pane();
		myListener = GuiConstants.GUI_MANAGER;
	}

	@Override
	public void initialize(Dimension2D containerSize) {
	        myMap.setMinSize(containerSize.getWidth(), containerSize.getHeight());
		myMap.setPrefSize(containerSize.getWidth(), containerSize.getHeight());
		myMap.getStyleClass().add("GameWorld");
		//myMap.setOnMouseClicked(event -> placeTower(event));

		addTestGameItem();
	}

	/*
	private void placeTower(MouseEvent mouseEvent) {
		myListener.placeTower(mouseEvent.getX(), mouseEvent.getY(), "DEFAULT TOWER");
	}*/

	@Override
	public Node getNode() {
		return myMap;
	}
	
	public void addEngineGroup(Group myEngineGroup){
		myMap.getChildren().add(myEngineGroup);
	}
	
	private void addTestGameItem() {
		ImageView imageView = new ImageView();
		String path = "gamePlayer/turretImages/Turret_2_1.png";
		imageView.setImage(new Image(path, 40, 40, false, false));
		SelectableGameItem testItem = new SelectableGameItem(1, new Point2D(100,100), imageView);
		myMap.getChildren().add(testItem.getNode());
	}

}
