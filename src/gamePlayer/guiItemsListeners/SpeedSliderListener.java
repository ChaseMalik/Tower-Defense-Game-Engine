package gamePlayer.guiItemsListeners;

import gamePlayer.guiItems.controlDockButtons.sliders.SpeedSlider;

public interface SpeedSliderListener {
	public void registerSpeedSlider(SpeedSlider slider);
    public void changeSpeed(double d);
}
