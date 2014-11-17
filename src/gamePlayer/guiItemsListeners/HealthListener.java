package gamePlayer.guiItemsListeners;

import javafx.beans.property.DoubleProperty;

/**
 * 
 * @author Greg Lyons
 *
 */

public interface HealthListener {

	public void bindHealth(DoubleProperty healthRemaining);

}
