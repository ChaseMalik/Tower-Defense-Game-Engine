package gamePlayer.mainClasses;

import gamePlayer.guiContainers.coreContainers.BottomContainer;
import gamePlayer.guiContainers.coreContainers.CenterContainer;
import gamePlayer.guiContainers.coreContainers.LeftContainer;
import gamePlayer.guiContainers.coreContainers.RightContainer;
import gamePlayer.guiContainers.coreContainers.TopContainer;
import gamePlayer.textGeneration.Text;
import gamePlayer.textGeneration.TextGenerator;
import java.io.File;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import Utilities.XMLParsing.XMLParser;
import Utilities.XMLParsing.XMLParserInstantiator;

public class GuiBuilder {
    private static GuiBuilder myReference = null;
    private static final String myPropertiesPath =  "./src/gamePlayer/properties/GuiBuilderProperties.XML";
    private XMLParser myParser;
    private TextGenerator myTextGen;

    private GuiBuilder() {
        myParser = XMLParserInstantiator.getInstance(new File(myPropertiesPath));
        myTextGen = TextGenerator.getInstance();
    }

    public static GuiBuilder getInstance() {
        if (myReference == null) {
            myReference = new GuiBuilder();
        }
        return myReference;
    }

    public void build (Stage stage) {
        List<Double> windowSize = myParser.getDoubleValuesFromTag("WindowSize");

        Group group = new Group();
        Scene scene = new Scene(group, windowSize.get(0), windowSize.get(1));
        String styleSheetPath = myParser.getValuesFromTag("WindowStyleSheet").get(0);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(this.getClass().getResource(styleSheetPath).toExternalForm());

        stage.setScene(scene);
        stage.setTitle(myTextGen.get(Text.VOOGASALAD));
        stage.setResizable(true);
        group.getChildren().add(initializeCoreContainers(windowSize));
        stage.show();
    }

    public Node initializeCoreContainers(List<Double> windowSize) {
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
