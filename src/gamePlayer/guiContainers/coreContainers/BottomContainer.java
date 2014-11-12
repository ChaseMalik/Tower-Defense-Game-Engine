package gamePlayer.guiContainers.coreContainers;

import gamePlayer.guiContainers.GuiContainer;
import gamePlayer.mainClasses.ExceptionHandler;
import gamePlayer.mainClasses.GuiElement;
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
        
        //set component size
        List<Double> sizeRatio = myParser.getDoubleValuesFromTag("SizeRatio");
        List<Double> mySize = Arrays.asList(containerSize.get(0)*sizeRatio.get(0),containerSize.get(1)*sizeRatio.get(1));
        this.setPrefSize(mySize.get(0),mySize.get(1));
        
        //add contained GUI elements
        List<String> myItems = myParser.getValuesFromTag("Items");
        ExceptionHandler handler = ExceptionHandler.getInstance();
        for (String item:myItems) {
            try {
                GuiElement element = (GuiElement) Class.forName(item).getConstructor().newInstance();
                element.initialize(mySize);
                this.getChildren().add(element.getNode());
            }
            catch (ReflectiveOperationException e) {
                handler.handle(e);
            }
        }
    }

    @Override
    public Node getNode () {
        return this;
    }   
}
