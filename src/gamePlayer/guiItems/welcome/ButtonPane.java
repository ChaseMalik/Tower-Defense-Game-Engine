package gamePlayer.guiItems.welcome;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.io.File;

import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import utilities.XMLParsing.XMLParser;

/**
 * 
 * @author brianbolze
 */

public class ButtonPane implements GuiItem {

	private XMLParser myParser;
	private VBox myPane;
	private Dimension2D mySize; //, buttonSize;
	
	@Override
	public void initialize(Dimension2D containerSize) {
		mySize = containerSize;
		String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML";
        myParser = new XMLParser(new File(propertiesPath)); 
		setupPane();
		setupButtons();
	}

	@Override
	public Node getNode() {
		return myPane;
	}
	
	private void setupPane() {
		myPane = new VBox();
		myPane.setMinSize(mySize.getWidth(), mySize.getHeight());
		myPane.setAlignment(Pos.CENTER);
		int padding = myParser.getIntegerValuesFromTag("Padding").get(0);
		myPane.setSpacing(padding);;
		myPane.getStyleClass().add("Button-Pane");
	}
	
	private void setupButtons() {
//		Dimension2D buttonRatio = myParser.getDimension("ButtonSizeRatio");
//		buttonSize = new Dimension2D(buttonRatio.getWidth()
//				* mySize.getWidth(), buttonRatio.getHeight()
//				* mySize.getHeight());
		for (String s : myParser.getValuesFromTag("ButtonNames")) {
			makeButton(s);
		}
	}
	
	private void makeButton(String name) {
		
		Button button = new Button(name);
//		button.setMinSize(buttonSize.getWidth(), buttonSize.getHeight());
//		button.setPrefSize(buttonSize.getWidth(), buttonSize.getHeight());
		button.setOnAction(event -> doStuff(name));
		
		myPane.getChildren().add(button);
	}
	
	private void doStuff(String name) {
		GuiConstants.WELCOME_MANAGER.newGame();
	}

}
