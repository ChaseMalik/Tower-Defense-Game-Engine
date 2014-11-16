package gamePlayer.guiContainers.coreContainers;

import gamePlayer.guiContainers.GuiContainer;
import gamePlayer.mainClasses.GuiElement;

import java.io.File;
import java.util.List;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import utilities.XMLParsing.XMLParser;
import utilities.reflection.Reflection;

/**
 * GuiContainer that hosts GuiElements at the center of the screen
 * @author allankiplagat
 *
 */
public class CenterContainer extends BorderPane implements GuiContainer {
    private XMLParser myParser;

    @Override
    public void initialize (Dimension2D containerSize) {
        myParser = new XMLParser(new File(myPropertiesPath+this.getClass().getSimpleName()+".XML")); 
        
        //set component size
        Dimension2D sizeRatio = myParser.getDimension("SizeRatio");
        Dimension2D mySize = new Dimension2D(containerSize.getWidth()*sizeRatio.getWidth(),
                                             containerSize.getHeight()*sizeRatio.getHeight());
        this.setPrefSize(mySize.getWidth(),mySize.getHeight());
        
        //add contained GUI elements
        List<String> myItems = myParser.getValuesFromTag("Items");
        for (String item:myItems) {
                GuiElement element = (GuiElement) Reflection.createInstance(item);
                element.initialize(mySize);
                this.setCenter(element.getNode());
        }
    }

    @Override
    public Node getNode () {
        return this;
    }
}
