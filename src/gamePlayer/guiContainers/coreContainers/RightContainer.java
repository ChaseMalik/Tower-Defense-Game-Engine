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
 * GuiContainer that hosts GuiElements on the right side of the screen
 * @author allankiplagat
 *
 */
public class RightContainer extends VBox implements GuiContainer {

    @Override
    public void initialize (Dimension2D containerSize) {

        CoreContainerSetup c = new CoreContainerSetup();
        this.getChildren().addAll(c.initialize(this, GuiConstants.RIGHT_CONTAINER_WIDTH,GuiConstants.RIGHT_CONTAINER_HEIGHT, containerSize,myPropertiesPath));
    }

    @Override
    public Node getNode () {
        return this;
    }

}