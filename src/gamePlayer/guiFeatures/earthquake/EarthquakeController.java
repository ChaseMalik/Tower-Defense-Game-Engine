// This entire file is part of my masterpiece.
// Brian Bolze (beb23)
package gamePlayer.guiFeatures.earthquake;

import gamePlayer.guiItemsListeners.EarthquakeListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import utilities.LeapMotion.LMController;

/**
 * 
 * @author brianbolze
 */
public class EarthquakeController {

	private double maxMag, magnitude;
	private final int numUpdates = 60;
	private EarthquakeStrategy strategy;
	private EarthquakeListener listener;

	public EarthquakeController(EarthquakeListener listener,
			double maximumManitude) {
		this.maxMag = maximumManitude;
		this.listener = listener;
		strategy = LMController.getInstance().deviceIsConnected() ? 
				new LMEarthquakeStrategy() :
				new MouseEarthquakeStrategy();
		magnitude = 0;
	}

	public void start(double time) {
		strategy.start(this);
		Timeline timeline = new Timeline();
		KeyFrame kf = new KeyFrame(Duration.seconds(time / numUpdates),
				event -> decrement());
		timeline.getKeyFrames().add(kf);
		timeline.setCycleCount(numUpdates);
		timeline.setAutoReverse(false);
		timeline.getKeyFrames().add(kf);
		timeline.setOnFinished(event -> stop());
		timeline.play();
	}
	
	protected void vibrate(double mag) {
		magnitude += (maxMag - magnitude) * mag;
	}

	private void stop() {
		listener.vibrate(-1);
		strategy.stop();
	}

	private void decrement() {
		if (magnitude - 0.6 >= 0)
			magnitude -= 0.6;
		listener.vibrate(magnitude);
	}

}
