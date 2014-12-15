// This entire file is part of my masterpiece.
// Allan Kiplagat

package gamePlayer.guiContainers.coreContainers;

import gamePlayer.guiContainers.GuiContainer;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * GuiContainer that hosts GuiElements at the top of the screen
 * 
 * @author allankiplagat
 *
 */
public class TopContainer extends HBox implements GuiContainer {

    @Override
    public void initialize(Dimension2D containerSize) {
        CoreContainerSetup c = new CoreContainerSetup();
        this.getChildren().
        addAll(c.initialize(this,GuiConstants.TOP_CONTAINER_WIDTH,GuiConstants.TOP_CONTAINER_HEIGHT,containerSize,myPropertiesPath));
    }

    @Override
    public Node getNode() {
        return this;
    }
}
