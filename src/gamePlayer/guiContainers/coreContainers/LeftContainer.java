package gamePlayer.guiContainers.coreContainers;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import Utilities.XMLParsing.XMLParser;
import Utilities.XMLParsing.XMLParserInstantiator;
import gamePlayer.guiContainers.GuiContainerInterface;
import gamePlayer.mainClasses.ExceptionHandler;
import gamePlayer.mainClasses.GuiElementInterface;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class LeftContainer extends VBox implements GuiContainerInterface {
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
                GuiElementInterface element = (GuiElementInterface) Class.forName(item).getConstructor().newInstance();
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
