package gamePlayer.guiItems.messageDisplay;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class MessageDisplay implements GuiItem {

	private Dimension2D mySize;
	private TextField textField;
	
	@Override
	public void initialize(Dimension2D containerSize) {
		mySize = containerSize;
		textField = new TextField();
		textField.setPrefSize(mySize.getWidth(), mySize.getHeight());
		textField.getStyleClass().add("MessageDisplay");
		textField.setText("Welcome to TowerDefense!");
		GuiConstants.GUI_MANAGER.registerMessageDisplayListener(this);
	}

	@Override
	public Node getNode() {
		return textField;
	}
	
	public void clear() {
		textField.clear();
	}
	
	public void showMessage(String message) {
		textField.setText(message);
	}

}
