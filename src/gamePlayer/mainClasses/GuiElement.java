package gamePlayer.mainClasses;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;

/**
 * Everything on the GUI is a GuiElement and this class defines universal GuiElement behavior
 * Known implementing classes: GuiItem and GuiContainer interfaces
 * @author allankiplagat
 *
 */
public interface GuiElement {
    /**
     * In this method, a GuiElement sets its node's size based on the containerSize 
     * and also initializes elements it hosts
     * @param containerSize size of the container within which this GuiElement is hosted
     */
    public abstract void initialize(Dimension2D containerSize);
    /**
     * @return this GuiElement's Node
     */
    public abstract Node getNode();
}
