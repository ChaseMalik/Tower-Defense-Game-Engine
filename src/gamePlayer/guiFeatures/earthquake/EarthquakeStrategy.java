package gamePlayer.guiFeatures.earthquake;

import gamePlayer.guiItemsListeners.EarthquakeListener;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;



public abstract class EarthquakeStrategy {

	private EarthquakeListener listener = GuiConstants.GUI_MANAGER;

	public void start() {
	}

	public void stop() {
	}
	
	public void swipe(double mag) {
		listener.vibrate(mag);
	}
	
}
