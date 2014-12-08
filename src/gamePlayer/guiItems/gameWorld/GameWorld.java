package gamePlayer.guiItems.gameWorld;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.scenes.pathBuilding.buildingPanes.BuildingPane;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItemsListeners.GameWorldListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import utilities.JavaFXutilities.imageView.StringToImageViewConverter;

/**
 * 
 * @author Greg Lyons
 * 
 *         GameWorld serves as the background in which the animation and
 *         interaction of all of the actors takes place.
 */

public class GameWorld implements GuiItem {

	private Pane myMap;
	private GameWorldListener myListener;
	private Set<SelectableGameItem> selectableGameItems;

	public GameWorld() {
		myMap = new Pane();
		myListener = GuiConstants.GUI_MANAGER;
		selectableGameItems = new HashSet<SelectableGameItem>();
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
	
	public void addSelectableGameItem(SelectableGameItem item) {
		selectableGameItems.add(item);
	}
	
	public Collection<SelectableGameItem> getSelectedItems() {
		
	}
	
	public void removeSelectableGameItem(SelectableGameItem item) {
		selectableGameItems.remove(item);
	}

	public void setBackground(String imagePath) {
		myMap.setStyle("-fx-background-image: url('file:" + imagePath + "');");
		ImageView background = StringToImageViewConverter.getImageView(myMap.getWidth(), myMap.getHeight(), imagePath);
		myMap.getChildren().add(background);
		background.toBack();
	}

	public void addEngineGroup(Group myEngineGroup) {
		myMap.getChildren().add(myEngineGroup);
	}

}
