package gamePlayer.guiFeatures.earthquake;

import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MouseEarthquakeStrategy implements EarthquakeStrategy {

	private EarthquakeController listener;
	private EventHandler<MouseEvent> clicker;
	
	@Override
	public void start(EarthquakeController listener) {
		this.listener = listener;
		clicker = event -> vibrate(0.6);
		GuiConstants.GUI_MANAGER.addEventFilter(MouseEvent.MOUSE_PRESSED, clicker);
	}

	@Override
	public void stop() {
		GuiConstants.GUI_MANAGER.removeEventFilter(MouseEvent.MOUSE_PRESSED, clicker);
	}

	@Override
	public void vibrate(double mag) {
		listener.vibrate(mag);
	}
	
}
