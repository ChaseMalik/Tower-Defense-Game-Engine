package gamePlayer.mainClasses;

import java.util.List;
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
    public abstract void initialize(List<Double> containerSize);
    public abstract Node getNode();
}
