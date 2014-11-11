package gamePlayer.mainClasses;

import gamePlayer.guiContainers.coreContainers.BottomContainer;
import gamePlayer.guiContainers.coreContainers.CenterContainer;
import gamePlayer.guiContainers.coreContainers.LeftContainer;
import gamePlayer.guiContainers.coreContainers.RightContainer;
import gamePlayer.guiContainers.coreContainers.TopContainer;
import gamePlayer.textGeneration.Text;
import gamePlayer.textGeneration.TextGenerator;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
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
        List<Integer> windowSize = myParser.getIntegerValuesFromTag("WindowSize");

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

    public Node initializeCoreContainers(List<Integer> windowSize) {
        BorderPane pane = new BorderPane();
        pane.setPrefSize(windowSize.get(0), windowSize.get(1));
        pane.setTop(new TopContainer(windowSize));
        pane.setLeft(new LeftContainer(windowSize));
        pane.setRight(new RightContainer(windowSize));
        pane.setBottom(new BottomContainer(windowSize));
        pane.setCenter(new CenterContainer(windowSize)); 
        return pane;
    }
}
