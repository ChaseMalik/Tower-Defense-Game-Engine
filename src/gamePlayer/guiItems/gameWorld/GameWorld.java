package gamePlayer.guiItems.gameWorld;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItemsListeners.GameWorldListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * 
 * @author Greg Lyons
 * 
 *         GameWorld serves as the background in which the animation and
 *         interaction of all of the actors takes place.
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
		myMap.setPrefSize(BuildingPane.DRAW_SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT);
		myListener.registerGameWorld(this);
	}

	@Override
	public Node getNode() {
		return myMap;
	}

	public Pane getMap() {
		return myMap;
	}

	public void setBackground(String imagePath) {
		myMap.setStyle("-fx-background-image: url('file:" + imagePath + "');");
	}

	public void addEngineGroup(Group myEngineGroup) {
		myMap.getChildren().add(myEngineGroup);
	}

}
