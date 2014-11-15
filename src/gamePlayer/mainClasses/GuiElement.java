package gamePlayer.mainClasses;

import javafx.geometry.Dimension2D;
import javafx.scene.Node;

/**
 * Class defines universal Gui element behavior that both containers and items have
 * @author allankiplagat
 *
 */
public interface GuiElement {
    /**
     * In this method, a GuiElement should set its size based on the componentSize
     * @param componentSize size of the component within which this GuiElement is hosted
     */
    public abstract void initialize(Dimension2D containerSize);
    public abstract Node getNode();
}
