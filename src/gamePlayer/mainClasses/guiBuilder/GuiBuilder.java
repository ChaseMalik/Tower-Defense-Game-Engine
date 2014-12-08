package gamePlayer.mainClasses.guiBuilder;

import gamePlayer.guiContainers.coreContainers.BottomContainer;
import gamePlayer.guiContainers.coreContainers.CenterContainer;
import gamePlayer.guiContainers.coreContainers.LeftContainer;
import gamePlayer.guiContainers.coreContainers.RightContainer;
import gamePlayer.guiContainers.coreContainers.TopContainer;
import java.io.File;
import java.util.List;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utilities.XMLParsing.XMLParser;

/**
 * Class builds a GUI recursively by calling 'initialize' on core GuiElements,
 * which in turn call 'initialize' on the elements they host and so on
 * 
 * @author allankiplagat, Brian Bolze
 *
 */
public class GuiBuilder {

	private static GuiBuilder myReference;
	private XMLParser myParser;

	private GuiBuilder() {
	}

	/**
	 * Returns an instance of a GuiBuilder that can build a GUI
	 * 
	 * @param propertiesPath
	 *            path to the GuiBuilder's XML properties file
	 */
	public static GuiBuilder getInstance() {
		if (myReference == null) {
			myReference = new GuiBuilder();
		}
		return myReference;
	}

	/**
	 * Constructs a GUI. IMPORTANT: Before calling this method, set the
	 * appropriate GUI_MANAGER constant in GuiConstants
	 * 
	 * @param stage
	 *            the stage in which to construct the GUI
	 */
	public Group build(Stage stage, String propertiesPath) {
		// set constants
		myParser = new XMLParser(new File(propertiesPath));
		GuiConstants.GUI_ELEMENT_PROPERTIES_PATH = myParser.getValuesFromTag(
				"GuiElementPropertiesPath").get(0);

		// TODO: TEXT_GEN already initialized in WelcomeScreenManager. Remove
		// line below
		//GuiConstants.TEXT_GEN = new TextGenerator(myParser.getValuesFromTag(
		//		"TextGeneratorPropertiesPath").get(0));
		
		Dimension2D windowSize = null;
		if (GuiConstants.DYNAMIC_SIZING) {
			windowSize = new Dimension2D(GuiConstants.WINDOW_WIDTH,
					GuiConstants.WINDOW_HEIGHT);
		} else {
			List<Double> sizing = myParser.getDoubleValuesFromTag("GuiSize");
			windowSize = new Dimension2D(sizing.get(0), sizing.get(1));
		}
				
		Group group = new Group();
		group.setAutoSizeChildren(true);
		group.getChildren().add(initializeCoreContainers(windowSize));

		Scene scene = new Scene(group, windowSize.getWidth(),
				windowSize.getHeight());
		setStyleSheet(scene);
		stage.setScene(scene);
		stage.titleProperty().bind(GuiConstants.MULTILANGUAGE.getStringProperty(GuiText.VOOGASALAD));

		// for now, no re-sizing window dynamically until dynamic window
		// resizing algorithm is written
		stage.setResizable(false);
		stage.show();

		return group;
	}

	private void setStyleSheet(Scene scene) {
		if (!myParser.getValuesFromTag("GuiStyleSheet").isEmpty()) {
			String styleSheetPath = myParser.getValuesFromTag("GuiStyleSheet")
					.get(0);
			scene.getStylesheets().add(
					this.getClass().getResource(styleSheetPath)
							.toExternalForm());
		}
	}

	private Node initializeCoreContainers(Dimension2D windowSize) {
		BorderPane pane = new BorderPane();

		pane.setPrefSize(windowSize.getWidth(), windowSize.getHeight());

		TopContainer top = new TopContainer();
		LeftContainer left = new LeftContainer();
		RightContainer right = new RightContainer();
		CenterContainer center = new CenterContainer();
		BottomContainer bottom = new BottomContainer();

		if (GuiConstants.DYNAMIC_SIZING) {
			top.initialize(windowSize);
			left.initialize(windowSize);
			right.initialize(windowSize);
			center.initialize(windowSize);
			bottom.initialize(windowSize);
		} else {
			List<Double> widths = myParser.getDoubleValuesFromTag("ContainerWidths");
			List<Double> heights = myParser.getDoubleValuesFromTag("ContainerHeights");
			top.initialize(new Dimension2D(windowSize.getWidth()*widths.get(0), windowSize.getHeight()*heights.get(0)));
			left.initialize(new Dimension2D(windowSize.getWidth()*widths.get(1), windowSize.getHeight()*heights.get(1)));
			right.initialize(new Dimension2D(windowSize.getWidth()*widths.get(2), windowSize.getHeight()*heights.get(2)));
			center.initialize(new Dimension2D(windowSize.getWidth()*widths.get(3), windowSize.getHeight()*heights.get(3)));
			bottom.initialize(new Dimension2D(windowSize.getWidth()*widths.get(4), windowSize.getHeight()*heights.get(4)));
		}

		pane.setTop(top);
		pane.setLeft(left);
		pane.setRight(right);
		pane.setBottom(bottom);
		pane.setCenter(center);

		return pane;
	}
}
