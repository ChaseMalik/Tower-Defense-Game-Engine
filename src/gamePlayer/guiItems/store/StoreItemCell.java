package gamePlayer.guiItems.store;

import gamePlayer.guiItems.GuiItem;
import javafx.beans.binding.Bindings;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StoreItemCell implements GuiItem {

	ImageView myImage;

	@Override
	public void initialize(Dimension2D containerSize) {

		Image image = new Image("file:LockedCell.png",
				containerSize.getWidth(), containerSize.getHeight(), false, false);
		Image hoverImage = new Image("file:TurretCell.png",
				containerSize.getWidth(), containerSize.getHeight(), false, false);

		myImage = new ImageView(image);

		myImage.imageProperty().bind(
				Bindings.when(myImage.hoverProperty()).then(hoverImage)
						.otherwise(image));

		myImage.setOnMouseClicked(event -> buyItem());
	}

	@Override
	public Node getNode() {
		return myImage;
	}

	// Send this to the controller with my ID
	private void buyItem() {
		System.out.println("Buy Item!");
	}

}
