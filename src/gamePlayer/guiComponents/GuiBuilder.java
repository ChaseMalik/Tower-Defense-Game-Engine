package gamePlayer.guiComponents;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import Utilities.XMLParsing.XMLParser;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GuiBuilder {
    private static GuiBuilder myReference = null;
    private static final String myPropertiesPath =  "./src/gamePlayer/properties/GuiBuilderProperties.XML";
    private XMLParser myParser;
    
    private GuiBuilder() {
        try {
            myParser = new XMLParser(new File(myPropertiesPath));
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error creating XML parser\n");
            e.printStackTrace();
        }
    }
    
    public static GuiBuilder getInstance() {
        if (myReference == null) {
            myReference = new GuiBuilder();
        }
        return myReference;
    }

    public void build (Stage myStage) {
            BorderPane pane = new BorderPane();
            List<Integer> windowSize = myParser.getIntegerValuesFromTag("WindowSize");
            
            System.out.println(windowSize.get(0));
            /*
            
            pane.setPrefSize(windowSize.get(0), windowSize.get(1));
            
            Scene scene = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT, Color.CORNSILK);
            scene.getStylesheets().add(STYLESHEET_PACKAGE + "style.css");
            stage.setScene(scene);
            stage.setTitle(textGen.get(TextGenerator.SLOGO));
            stage.setResizable(false);
            stage.show();
            return pane;*/
    }
}
