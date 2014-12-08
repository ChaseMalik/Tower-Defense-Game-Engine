package gamePlayer.guiFeatures.earthquake;

import gamePlayer.guiItemsListeners.EarthquakeListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;

public class EarthquakeController {
	
	public static double maxMag;
	
	private EarthquakeStrategy strategy;
	private EarthquakeListener listener;
	private DoubleProperty magnitudeProperty;
	
	public EarthquakeController(double maxMag, EarthquakeStrategy strategy, EarthquakeListener listener) {
		this.maxMag = maxMag;
		this.strategy = strategy;
		this.listener = listener;
		magnitudeProperty = new SimpleDoubleProperty(0);
	}
	
	public void start(double time) {
		strategy.start(this);
		Timeline timeline = new Timeline();
		KeyFrame kf = new KeyFrame(Duration.seconds(time/60), event -> decrement());
		timeline.getKeyFrames().add(kf);
		timeline.setCycleCount(60);
		timeline.setAutoReverse(false);
		timeline.getKeyFrames().add(kf);
		timeline.setOnFinished(event -> stop());
		timeline.play();
	}
	
	private void decrement() {
		double toValue = magnitudeProperty.get() - 0.4;
		if (toValue >= 0)
			magnitudeProperty.set(toValue);
		listener.vibrate(magnitudeProperty.get());
	}

	public void stop() {
		listener.vibrate(-1);
		strategy.stop();
	}
	
	public void vibrate(double mag) {
		double currValue = magnitudeProperty.get();
		magnitudeProperty.set(currValue + ((maxMag-currValue) * mag));
	}
	
}
