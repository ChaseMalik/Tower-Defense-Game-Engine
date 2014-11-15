package gamePlayer.mainClasses.guiBuilder;

import gamePlayer.guiContainers.coreContainers.BottomContainer;
import gamePlayer.guiContainers.coreContainers.CenterContainer;
import gamePlayer.guiContainers.coreContainers.LeftContainer;
import gamePlayer.guiContainers.coreContainers.RightContainer;
import gamePlayer.guiContainers.coreContainers.TopContainer;
import gamePlayer.mainClasses.GuiManager;
import java.io.File;
import java.util.List;
import utilities.XMLParsing.XMLParser;
import utilities.textGenerator.TextGenerator;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GuiBuilder {
    private XMLParser myParser;

    private GuiBuilder(String propertiesPath) {
        myParser = new XMLParser(new File(propertiesPath)); 
    }

    public static GuiBuilder getInstance(String propertiesPath) {
        return new GuiBuilder(propertiesPath);
    }

    public void build (Stage stage, GuiManager guiManager) {
        //set constants
        GuiConstants.GUI_CONTROLLER = guiManager;
        GuiConstants.GUI_CONTROLLER.setStage(stage);
        GuiConstants.TEXT_GEN = new TextGenerator(myParser.getValuesFromTag("TextGeneratorPropertiesPath").get(0));
        GuiConstants.TEXT_GEN.setLanguage("en");
        
        List<Double> windowSize = myParser.getDoubleValuesFromTag("GuiSize");

        Group group = new Group();
        group.setAutoSizeChildren(true);
        group.getChildren().add(initializeCoreContainers(windowSize));

        Scene scene = new Scene(group, windowSize.get(0), windowSize.get(1));
        setStyleSheet(scene);
        stage.setScene(scene);
        stage.setTitle(GuiConstants.TEXT_GEN.get(GuiText.VOOGASALAD));

        //for now, no re-sizing window dynamically until dynamic window resizing algorithm is written
        stage.setResizable(false);
        stage.show();
    }

    private void setStyleSheet(Scene scene) {
        String styleSheetPath = myParser.getValuesFromTag("GuiStyleSheet").get(0);
        if (!styleSheetPath.equals("")) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(this.getClass().getResource(styleSheetPath).toExternalForm());
        }
    }

    private Node initializeCoreContainers(List<Double> windowSize) {
        BorderPane pane = new BorderPane();

        pane.setPrefSize(windowSize.get(0), windowSize.get(1));

        TopContainer top = new TopContainer(); 
        top.initialize(windowSize); pane.setTop(top);

        LeftContainer left = new LeftContainer(); 
        left.initialize(windowSize); pane.setLeft(left);

        RightContainer right = new RightContainer(); 
        right.initialize(windowSize); pane.setRight(right);

        BottomContainer bottom = new BottomContainer(); 
        bottom.initialize(windowSize); pane.setBottom(bottom);

        CenterContainer center = new CenterContainer(); 
        center.initialize(windowSize); pane.setCenter(center); 

        return pane;
    }
}
