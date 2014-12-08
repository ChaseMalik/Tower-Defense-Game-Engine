package gamePlayer.guiFeatures.earthquake;

public abstract class EarthquakeStrategy {

	private EarthquakeController listener;

	public void start(EarthquakeController listener) {
		this.listener = listener;
	}

	public void stop() {
	}
	
	public void swipe(double mag) {
		listener.vibrate(mag);
	}
	
}
