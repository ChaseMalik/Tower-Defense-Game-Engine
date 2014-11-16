package gamePlayer.guiItems.store;

import gamePlayer.guiItems.GuiItem;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class UpgradeStore implements GuiItem {
	
	private String myTowerID;
	private Pane root;
	
	public UpgradeStore(String towerID) {
		myTowerID = towerID;
	}

	@Override
	public void initialize(Dimension2D containerSize) {
		root = new VBox();
		root.getChildren().add(new Rectangle(containerSize.getWidth(), containerSize.getHeight()));
	}

	@Override
	public Node getNode() {
		return root;
	}
}
