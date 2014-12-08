package gamePlayer.guiFeatures.earthquake;

import gamePlayer.guiFeatures.LMController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class LMEarthquakeStrategy extends EarthquakeStrategy {

	private LMController controller;
	private EventHandler<ActionEvent> swiper;
	
	@Override
	public void start() {
		controller = LMController.getInstance();
		swiper = event -> swipe(0.3);
		controller.setTimeLimit(false);
		controller.onSwipeDown(swiper);
		controller.onSwipeUp(swiper);
	}
	
	public void stop() {
		controller.setTimeLimit(true);
		controller.removeOnSwipeDown(swiper);
		controller.removeOnSwipeUp(swiper);
	}
	
}
