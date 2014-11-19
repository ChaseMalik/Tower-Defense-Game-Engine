package gamePlayer.previousVersionCode.guiItems.store;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.previousVersionCode.StoreListener;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StoreItemCell implements GuiItem {

	private String myName;
	private ImageView myImage;
	private StoreListener myListener = 	GuiConstants.GUI_MANAGER;

	public StoreItemCell(String name) {
		myName = name;
	}
	
	@Override
	public void initialize(Dimension2D containerSize) {
		ResourceBundle resources = null;
		try {
			resources = ResourceBundle.getBundle("spriteResources."+myName);
		} catch (Exception e) {
			System.out.println("Error reading properties file");
			System.exit(1); //Kill Program
		}

		String imagePath = (String)resources.getObject("Store_Cell_Image");
		String hoverImagePath = (String)resources.getObject("Store_Cell_Hover_Image");
		
		Image image = new Image("file:"+imagePath,
				containerSize.getWidth(), containerSize.getHeight(), false, false);
		Image hoverImage = new Image("file:"+hoverImagePath,
				containerSize.getWidth(), containerSize.getHeight(), false, false);

		myImage = new ImageView(image);

		myImage.imageProperty().bind(Bindings
						.when(myImage.hoverProperty())
						.then(hoverImage)
						.otherwise(image));

		myImage.setOnMouseClicked(event -> buyItem());
		
	}

	@Override
	public Node getNode() {
		return myImage;
	}

	private void buyItem() {
		if (myName.equals("LockedItem")) return;
		myListener.buyItem(myName);
	}

}
