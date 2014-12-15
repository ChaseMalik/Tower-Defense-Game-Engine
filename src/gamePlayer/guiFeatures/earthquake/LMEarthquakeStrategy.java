package gamePlayer.guiFeatures.earthquake;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import utilities.LeapMotion.LMController;

public class LMEarthquakeStrategy implements EarthquakeStrategy {

	private LMController controller;
	private EarthquakeController listener;
	private EventHandler<ActionEvent> swiper;
	
	@Override
	public void start(EarthquakeController listener) {
		this.listener = listener;
		controller = LMController.getInstance();
		swiper = event -> vibrate(0.05);
		controller.setTimeLimit(false);
		controller.onSwipeDown(swiper);
		controller.onSwipeUp(swiper);
	}
	
	@Override
	public void stop() {
		controller.setTimeLimit(true);
		controller.removeOnSwipeDown(swiper);
		controller.removeOnSwipeUp(swiper);
	}

	@Override
	public void vibrate(double mag) {
		listener.vibrate(mag);
	}
	
}
