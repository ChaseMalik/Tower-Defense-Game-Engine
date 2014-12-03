package gamePlayer.guiItems.messageDisplay;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * 
 * @author brianbolze
 */
public class MessageDisplay implements GuiItem {

	private Dimension2D mySize;
	private TextField textField;
	
	@Override
	public void initialize(Dimension2D containerSize) {
		mySize = new Dimension2D(containerSize.getWidth()*0.22, containerSize.getHeight());
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
	
	public void showMessage(String message, boolean error) {
		textField.setText(message);
		if (error) textField.setStyle("-fx-text-fill: Red;");
		else textField.setStyle("-fx-text-fill: Black;");
	}

}
