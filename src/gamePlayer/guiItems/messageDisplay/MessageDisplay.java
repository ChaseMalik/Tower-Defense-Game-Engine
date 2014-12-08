package gamePlayer.guiItems.messageDisplay;

import java.io.File;

import utilities.JavaFXutilities.ratioSizing.RatiosToDim;
import utilities.XMLParsing.XMLParser;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.guiItems.towerUpgrade.StandardBorder;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * 
 * @author brianbolze
 */
public class MessageDisplay implements GuiItem {

	public static final String DEFAULT = "Welcome to TowerDefense!";
	private Dimension2D mySize;
	private TextArea textArea;
	private XMLParser myParser;
	
	@Override
	public void initialize(Dimension2D containerSize) {
		String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML";
        myParser = new XMLParser(new File(propertiesPath)); 
        Dimension2D sizeRatio = myParser.getDimension("SizeRatio");
		mySize = RatiosToDim.convert(containerSize, sizeRatio);
		textArea = new TextArea();
		textArea.setPrefSize(mySize.getWidth(), mySize.getHeight());
		textArea.getStyleClass().add("MessageDisplay");
		textArea.setText(DEFAULT);
		textArea.setWrapText(true);
		textArea.setFont(Font.font("sans-serif", FontWeight.BOLD, 12.0));
		textArea.toFront();
		textArea.setBorder(StandardBorder.get(Color.BLACK));
		GuiConstants.GUI_MANAGER.registerMessageDisplayListener(this);
	}

	@Override
	public Node getNode() {
		return textArea;
	}
	
	public void clear() {
		textArea.clear();
	}
	
	public void showMessage(String message, boolean error) {
		textArea.setText(message);
		if (error) textArea.setStyle("-fx-text-fill: Red;");
		else textArea.setStyle("-fx-text-fill: Black;");
	}

}
