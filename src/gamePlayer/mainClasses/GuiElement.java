package gamePlayer.mainClasses;

import java.util.List;

/**
 * Class defines universal Gui element behavior that both containers and items have
 * @author allankiplagat
 *
 */
public interface GuiElement {
    /**
     * GuiElement should set its size based on the componentSize
     * @param componentSize size of the component within which this GuiElement is hosted
     */
    public abstract void initialize(List<Double> componentSize);
}
