package gamePlayer.guiContainers.coreContainers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import Utilities.XMLParsing.XMLParser;
import gamePlayer.guiContainers.GuiContainer;
import javafx.scene.layout.HBox;

public class BottomContainer extends HBox implements GuiContainer {
    private XMLParser myParser;
    
    public BottomContainer (List<Integer> windowSize) {
        /**
        try {
            myParser = new XMLParser(new File(myPropertiesPath));
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error creating XML parser\n");
            e.printStackTrace();
        }**/
    }

    @Override
    public void initialize () {
        /*List<Integer> containerSize = myPars
        this.setPrefSize(myPa, arg1);*/
    }

}
