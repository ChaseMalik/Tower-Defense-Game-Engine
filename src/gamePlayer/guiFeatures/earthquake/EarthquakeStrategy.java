// This entire file is part of my masterpiece.
// Brian Bolze (beb23)
package gamePlayer.guiFeatures.earthquake;

public interface EarthquakeStrategy {
	public void start(EarthquakeController listener);
	public void stop();
	public void vibrate(double mag);
}
