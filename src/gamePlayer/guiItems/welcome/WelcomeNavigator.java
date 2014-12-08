package gamePlayer.guiItems.welcome;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.io.File;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import utilities.XMLParsing.XMLParser;
import utilities.reflection.Reflection;

public class WelcomeNavigator implements GuiItem {
	
	private XMLParser myParser;
	private Dimension2D mySize;
	private HBox navPane;
	private int numScenes, currentScene;

	@Override
	public void initialize(Dimension2D containerSize) {
		mySize = containerSize;
		String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML";
		myParser = new XMLParser(new File(propertiesPath)); 
		setupNavPane();
		setupContents();
		// GuiConstants.WELCOME_MANAGER.registerWelcomeNavigator(this);
	}

	@Override
	public Node getNode() {
		return navPane;
	}
	
	public void deviceConnected(boolean connected) {
		if (!connected) {
			setupContents();
			return;
		}
		List<String> itemClasses = myParser.getValuesFromTag("LMSceneItems");
		GuiItem item;
		for (int i = 0; i < numScenes; i++) {
			item = (GuiItem) Reflection.createInstance(itemClasses.get(i));
			item.initialize(new Dimension2D(mySize.getWidth(), mySize.getHeight()));
			navPane.getChildren().remove(i);
			navPane.getChildren().add(i, item.getNode());
		}
		navPane.setTranslateX(-mySize.getWidth()*(currentScene-1));
	}
	
	private void setupNavPane() {
		navPane = new HBox();
		numScenes = myParser.getIntegerValuesFromTag("NumScenes").get(0);
		currentScene = 0;
		navPane.setMinSize(mySize.getWidth()*numScenes, mySize.getHeight());
		navPane.setAlignment(Pos.CENTER_LEFT);
	}
	
	private void setupContents() {

		List<String> itemClasses = myParser.getValuesFromTag("BasicSceneItems");
		List<Double> forwardPaneSize = myParser.getDoubleValuesFromTag("ForwardPanelSizeRatio");
		Button forwardButton;
		GuiItem item;
		HBox scene;
		
		for (int i = 0; i < numScenes; i++) {
			item = (GuiItem) Reflection.createInstance(itemClasses.get(i));
			item.initialize(new Dimension2D((1-forwardPaneSize.get(0))*mySize.getWidth(), mySize.getHeight()));
			
			forwardButton = new Button(">");
			forwardButton.setMinSize(forwardPaneSize.get(0)*mySize.getWidth(), mySize.getHeight() * forwardPaneSize.get(1));
			int fromScene = i;
			forwardButton.setOnAction(event -> shift(fromScene, fromScene + 1));
			
			scene = new HBox(item.getNode(), forwardButton);
			
			navPane.getChildren().add(scene);
		}
		navPane.setTranslateX(mySize.getWidth());
	}
	
	private void shift(int fromScene, int toScene) {
		System.out.println("hello!");
		if (toScene == numScenes)
			return;
		
		TranslateTransition tt = new TranslateTransition(Duration.millis(350), navPane);
		tt.setFromX(-mySize.getWidth()*(fromScene-1));
		tt.setToX(-mySize.getWidth()*(toScene-1));
	    tt.setCycleCount(1);
	    tt.setAutoReverse(false);
	    tt.play();
	}

}
