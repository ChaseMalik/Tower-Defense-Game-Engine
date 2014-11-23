package gamePlayer.mainClasses.guiBuilder;

import gamePlayer.guiContainers.coreContainers.BottomContainer;
import gamePlayer.guiContainers.coreContainers.CenterContainer;
import gamePlayer.guiContainers.coreContainers.LeftContainer;
import gamePlayer.guiContainers.coreContainers.RightContainer;
import gamePlayer.guiContainers.coreContainers.TopContainer;

import java.io.File;

import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utilities.XMLParsing.XMLParser;
import utilities.textGenerator.TextGenerator;

/**
 * Class builds a GUI recursively by calling 'initialize' on core GuiElements, 
 * which in turn call 'initialize' on the elements they host and so on
 * @author allankiplagat
 *
 */
public class GuiBuilder {
    private XMLParser myParser;

    private GuiBuilder(String propertiesPath) {
        myParser = new XMLParser(new File(propertiesPath)); 
    }

    /**
     * Returns an instance of a GuiBuilder that can build a GUI 
     * @param propertiesPath path to the GuiBuilder's XML properties file
     */
    public static GuiBuilder getInstance(String propertiesPath) {
        return new GuiBuilder(propertiesPath);
    }

    /**
     * Constructs a GUI. IMPORTANT: Before calling this method, set the appropriate 
     * GUI_MANAGER constant in GuiConstants
     * @param stage the stage in which to construct the GUI
     */
    public Group build (Stage stage) {
        //set constants
        GuiConstants.TEXT_GEN = new TextGenerator(myParser.getValuesFromTag("TextGeneratorPropertiesPath").get(0));
        Dimension2D windowSize = myParser.getDimension("GuiSize");
        
        Group group = new Group();
        group.setAutoSizeChildren(true);
        group.getChildren().add(initializeCoreContainers(windowSize));

        Scene scene = new Scene(group, windowSize.getWidth(), windowSize.getHeight());
        setStyleSheet(scene);
        stage.setScene(scene);
        stage.setTitle(GuiConstants.TEXT_GEN.get(GuiText.VOOGASALAD));

        //for now, no re-sizing window dynamically until dynamic window resizing algorithm is written
        stage.setResizable(false);
        stage.show();
        
        return group;
    }

    private void setStyleSheet(Scene scene) {
        if (!myParser.getValuesFromTag("GuiStyleSheet").isEmpty()) {
            String styleSheetPath = myParser.getValuesFromTag("GuiStyleSheet").get(0);
            scene.getStylesheets().add(this.getClass().getResource(styleSheetPath).toExternalForm());
        }
    }

    private Node initializeCoreContainers(Dimension2D windowSize) {
        BorderPane pane = new BorderPane();

        pane.setPrefSize(windowSize.getWidth(), windowSize.getHeight());

        TopContainer top = new TopContainer(); 
        top.initialize(windowSize); pane.setTop(top);

        LeftContainer left = new LeftContainer(); 
        left.initialize(windowSize); pane.setLeft(left);

        RightContainer right = new RightContainer(); 
        right.initialize(windowSize); pane.setRight(right);

        CenterContainer center = new CenterContainer(); 
        center.initialize(windowSize); pane.setCenter(center); 
        
        BottomContainer bottom = new BottomContainer(); 
        bottom.initialize(windowSize); pane.setBottom(bottom);

        return pane;
    }
}
