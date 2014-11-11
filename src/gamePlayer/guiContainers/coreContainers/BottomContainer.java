package gamePlayer.guiContainers.coreContainers;

import gamePlayer.guiContainers.GuiContainer;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import Utilities.XMLParsing.XMLParser;
import Utilities.XMLParsing.XMLParserInstantiator;

public class BottomContainer extends HBox implements GuiContainer {
    private XMLParser myParser;

    @Override
    public void initialize (List<Double> containerSize) {
        myParser = XMLParserInstantiator.
                getInstance(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML"));
        List<Double> sizeRatio = myParser.getDoubleValuesFromTag("SizeRatio");
        List<Double> mySize = Arrays.asList(containerSize.get(0)*sizeRatio.get(0),containerSize.get(1)*sizeRatio.get(1));
        this.setPrefSize(mySize.get(0),mySize.get(1));
        
        //add contained elements
        List<String> myItems = myParser.getValuesFromTag("Items");
        for (String item:myItems) {
            //(GuiElement) Class.forName(item).getConstructor(new Class[]{String.class,int.class}).newInstance(property.description,property.odds)
        }
    }

    @Override
    public Node getNode () {
        return this;
    }   
}
