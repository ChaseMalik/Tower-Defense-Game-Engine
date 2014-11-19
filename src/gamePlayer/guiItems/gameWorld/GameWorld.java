package gamePlayer.guiItems.gameWorld;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItemsListeners.TowerPlaceListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;


/**
 * 
 * @author Greg Lyons
 * 
 * GameWorld serves as the background in which the animation and interaction of all of the actors takes place.
 */


public class GameWorld implements GuiItem {
	
	Pane myMap;
	TowerPlaceListener myListener;

	public GameWorld() {
		myMap = new Pane();
		myListener = GuiConstants.GUI_MANAGER;
	}

	@Override
	public void initialize(Dimension2D containerSize) {
		myMap.setPrefSize(containerSize.getWidth(), containerSize.getHeight());
		
		myMap.setOnMouseClicked(event -> placeTower(event));
	}

	private void placeTower(MouseEvent mouseEvent) {
		myListener.placeTower(mouseEvent.getX(), mouseEvent.getY(), "DEFAULT TOWER");
	}

	@Override
	public Node getNode() {
		return myMap;
	}
	
	public void addEngineGroup(Group myEngineGroup){
		myMap.getChildren().add(myEngineGroup);
	}

}
