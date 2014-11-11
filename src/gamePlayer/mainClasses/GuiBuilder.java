package gamePlayer.mainClasses;

import gamePlayer.textGeneration.Text;
import gamePlayer.textGeneration.TextGenerator;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import Utilities.XMLParsing.XMLParser;

public class GuiBuilder {
    private static GuiBuilder myReference = null;
    private static final String myPropertiesPath =  "./src/gamePlayer/properties/GuiBuilderProperties.XML";
    private XMLParser myParser;
    private TextGenerator myTextGen;
    
    private GuiBuilder() {
        try {
            myParser = new XMLParser(new File(myPropertiesPath));
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error creating XML parser\n");
            e.printStackTrace();
        }
        
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
            scene.getStylesheets().add(myParser.getValuesFromTag("WindowStyleSheet").get(0));
            stage.setScene(scene);
            
            stage.setTitle(myTextGen.get(Text.VOOGASALAD));
            stage.setResizable(true);
            stage.show();
    }
}
