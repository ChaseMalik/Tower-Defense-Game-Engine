package gamePlayer.guiItems.welcome;

import gamePlayer.guiItems.GuiItem;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;

import java.io.File;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import utilities.XMLParsing.XMLParser;

/**
 * 
 * @author brianbolze
 */

public class ClickAnywherePane implements GuiItem {

	private XMLParser myParser;
	private Dimension2D mySize;
	private VBox myPane;
	private ProgressIndicator indicator;
	private Label myText;
	private Animation textAnimation;
	
	@Override
	public void initialize(Dimension2D containerSize) {
		mySize = containerSize;
		String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML";
        myParser = new XMLParser(new File(propertiesPath)); 
		setupPane();
		setupText();
		setupStatusIndicator();
		myPane.setOnMouseReleased(event -> mouseListener());
	}

	@Override
	public Node getNode() {
		return myPane;
	}
	
	private void setupPane() {
		myPane = new VBox();
		myPane.setPrefSize(mySize.getWidth(), mySize.getHeight());
		myPane.setAlignment(Pos.CENTER);
		myPane.setSpacing(myParser.getDoubleValuesFromTag("Padding").get(0));
		myPane.getStyleClass().add("ClickAnywherePane");
	}
	
	private void setupText() {
		String text = myParser.getValuesFromTag("LabelText").get(0);
		myText = new Label(text);
		myText.getStyleClass().add("ClickAnywherePane");
		textAnimation = new FadeTransition(Duration.millis(600), myText);
		((FadeTransition)textAnimation).setFromValue(1);
		((FadeTransition)textAnimation).setToValue(0);
		textAnimation.setAutoReverse(true);
		textAnimation.setCycleCount(Animation.INDEFINITE);
		textAnimation.play();
		myPane.getChildren().add(myText);
	}
	
	private void setupStatusIndicator() {
		indicator = new ProgressIndicator();
		indicator.setVisible(false);
		myPane.getChildren().add(indicator);
	}
	
	private void mouseListener() {
		myPane.setOnMouseReleased(null);
		textAnimation.stop();
		indicator.setVisible(true);
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> dummyLoop(timeline)));
		timeline.play();
	}
	
	private void dummyLoop(Timeline timeline) {
		if (timeline.getTotalDuration().toSeconds() >= 2) {
			timeline.stop();
			//GuiConstants.WELCOME_MANAGER.newGame();
		}
	}
	
}
