package gamePlayer.guiContainers.coreContainers;

import gamePlayer.guiContainers.GuiContainer;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.guiBuilder.GuiElement;

import java.io.File;
import java.util.List;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import utilities.XMLParsing.XMLParser;
import utilities.reflection.Reflection;

/**
 * GuiContainer that hosts GuiElements at the bottom of the screen
 * @author allankiplagat
 *
 */
public class BottomContainer extends HBox implements GuiContainer {
    private XMLParser myParser;

    @Override
    public void initialize (Dimension2D containerSize) {
    	String propertiesPath = GuiConstants.GUI_ELEMENT_PROPERTIES_PATH + myPropertiesPath+this.getClass().getSimpleName()+".XML";
        myParser = new XMLParser(new File(propertiesPath)); 

        //set component size
        Dimension2D sizeRatio = myParser.getDimension("SizeRatio");
        //Dimension2D mySize = new Dimension2D(containerSize.getWidth()*sizeRatio.getWidth(),
        //                                     containerSize.getHeight()*sizeRatio.getHeight());
        
        Dimension2D mySize = new Dimension2D(GuiConstants.BOTTOM_CONTAINER_WIDTH,GuiConstants.BOTTOM_CONTAINER_HEIGHT);
        
        this.setMinSize(mySize.getWidth(),mySize.getHeight());
        this.setPrefSize(mySize.getWidth(),mySize.getHeight());
        
        //add contained GUI elements
        List<String> myItems = myParser.getValuesFromTag("Items");
        for (String item:myItems) {
                GuiElement element = (GuiElement) Reflection.createInstance(item);
                element.initialize(mySize);
                this.getChildren().add(element.getNode());
        }
    }

    @Override
    public Node getNode () {
        return this;
    }   
}
