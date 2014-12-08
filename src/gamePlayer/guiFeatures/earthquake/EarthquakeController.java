package gamePlayer.guiFeatures.earthquake;

import gamePlayer.guiItemsListeners.EarthquakeListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class EarthquakeController {
	
	EarthquakeStrategy strategy;
	EarthquakeListener listener;
	
	public EarthquakeController(EarthquakeStrategy strategy, EarthquakeListener listener) {
		this.strategy = strategy;
		this.listener = listener;
	}
	
	public void start(double time) {
		strategy.start();
		Timeline timeline = new Timeline();
		KeyFrame kf = new KeyFrame(Duration.seconds(time/60));
		timeline.getKeyFrames().add(kf);
		timeline.setCycleCount(60);
		timeline.setAutoReverse(false);
		timeline.getKeyFrames().add(kf);
		timeline.setOnFinished(event -> stop());
		timeline.play();
	}
	
	public void stop() {
		strategy.stop();
	}
	
	public void vibrate(double magnitude) {
		listener.vibrate(magnitude);
	}
	
}
