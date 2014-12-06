package gamePlayer.guiItems.gameWorld;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gamePlayer.Listeners.GameWorldListener;
import gamePlayer.guiItems.GuiItem;
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

//	private void addTestGameItems() {
//
//		ImageView imageView1 = new ImageView();
//		String path1 = "gamePlayer/turretImages/Turret_2_1.png";
//		imageView1.setImage(new Image(path1, 60, 60, false, false));
//		SelectableGameItem testItem1 = new SelectableGameItem(1, new Point2D(
//				250, 250), imageView1);
//
//		ImageView imageView2 = new ImageView();
//		String path2 = "gamePlayer/turretImages/Turret_3_2.png";
//		imageView2.setImage(new Image(path2, 40, 40, false, false));
//		SelectableGameItem testItem2 = new SelectableGameItem(15, new Point2D(
//				100, 100), imageView2);
//		myMap.getChildren().addAll(testItem1.getNode(), testItem2.getNode());
//	}

}
