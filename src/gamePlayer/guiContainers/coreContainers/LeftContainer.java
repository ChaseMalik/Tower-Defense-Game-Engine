package gamePlayer.guiContainers.coreContainers;

import gamePlayer.guiContainers.GuiContainer;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.guiBuilder.GuiElement;

import java.io.File;
import java.util.List;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import utilities.XMLParsing.XMLParser;
import utilities.reflection.Reflection;

/**
 * GuiContainer that hosts GuiElements on the left side of the screen
 * @author allankiplagat
 *
 */
public class LeftContainer extends VBox implements GuiContainer {
   
	private XMLParser myParser;

    @Override
    public void initialize (Dimension2D containerSize) { 	
    	CoreContainerSetup c = new CoreContainerSetup();
		c.initialize(this, GuiConstants.LEFT_CONTAINER_WIDTH, GuiConstants.LEFT_CONTAINER_HEIGHT, containerSize);
		this.getChildren().addAll(c.getChildList());
    }

    @Override
    public Node getNode () {
        return this;
    }
}
