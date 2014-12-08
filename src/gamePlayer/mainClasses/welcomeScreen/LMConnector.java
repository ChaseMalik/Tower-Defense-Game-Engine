package gamePlayer.mainClasses.welcomeScreen;

import gamePlayer.guiFeatures.LMController;
import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.ExceptionHandling.ExceptionHandler;

import java.io.File;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utilities.XMLParsing.XMLParser;

/**
 * 
 * @author brianbolze
 */
public class LMConnector implements GuiItem {

	private XMLParser myParser;
	private double mySpacing;
	private Dimension2D mySize;
	private VBox root;
	private BooleanProperty connectedProperty;

	@Override
	public void initialize(Dimension2D containerSize) {
		mySize = containerSize;
		String propertiesPath = "src/gamePlayer/properties/welcome/guiItems/LMConnector.XML";
		myParser = new XMLParser(new File(propertiesPath));
		connectedProperty = new SimpleBooleanProperty(false);
		
		setupPane();
		setupLabel();
		setupButton();
	}

	@Override
	public Node getNode() {
		return root;
	}
	
	public void deviceConnected(boolean connected) {
		connectedProperty.set(connected);
	}
	
	/*
	 * Called by the button to attempt a device connection
	 */
	private void connect() {
		if (LMController.getInstance().deviceIsConnected())
			return;
		LMController.getInstance().activateDevice();
		LMController.getInstance().onConnect(event -> deviceConnected(true));;
	}
	
	private void setupPane() {
		root = new VBox();
		root.setPrefSize(mySize.getWidth(), mySize.getHeight());
		mySpacing = myParser.getDoubleValuesFromTag("Spacing").get(0);
		root.setSpacing(mySpacing);
		root.setPadding(new Insets(mySpacing));
		root.setAlignment(Pos.CENTER_RIGHT);
		root.getStyleClass().add("LMConnectBox");
	}

	private void setupLabel() {
		List<String> text = myParser.getValuesFromTag("LabelText");
		Label label = new Label();
		label.textProperty().bind(
				Bindings.when(connectedProperty).then(text.get(1))
						.otherwise(text.get(0)));
		label.getStyleClass().add("LMConnectBox");
		root.getChildren().add(label);
	}

	private void setupButton() {
		
		HBox hbox = new HBox();
		hbox.setSpacing(mySpacing);
		hbox.setAlignment(Pos.CENTER_RIGHT);
		
		String imagePath = myParser.getValuesFromTag("CheckMarkImage").get(0);
		List<Double> imageSize = myParser.getDoubleValuesFromTag("CheckMarkSize");
		ImageView checkmark = new ImageView();
		checkmark.setImage(new Image(imagePath, imageSize.get(0), imageSize.get(1), false, false));
		checkmark.visibleProperty().bind(connectedProperty);
		
		Button iconButton = new Button("Connect");
		List<Double> iconSize = myParser.getDoubleValuesFromTag("IconSize");
		iconButton.setPrefSize(iconSize.get(0), iconSize.get(1));
		ImageView iconImage = new ImageView();
		List<String> imagePaths = myParser.getValuesFromTag("IconImages");
		Image image1 = null;
		Image image2 = null;
		try {
			image1 = new Image(imagePaths.get(0), iconSize.get(0), iconSize.get(1), false, false);
			image2 = new Image(imagePaths.get(1), iconSize.get(0), iconSize.get(1), false, false);
		} catch (NullPointerException npe) {
			ExceptionHandler.getInstance().handle(npe);
		}
		iconImage.setImage(image1);
		iconImage.imageProperty().bind(
				Bindings.when(connectedProperty)
				.then(image2)
				.otherwise(image1));
		iconButton.setGraphic(iconImage);
		iconButton.setOnAction(event -> connect());	
		iconButton.getStyleClass().add("LMConnectButton");
		
		hbox.getChildren().addAll(checkmark, iconButton);
		root.getChildren().add(hbox);
	}

}
