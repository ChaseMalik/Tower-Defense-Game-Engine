package gamePlayer.guiItemsListeners;

import gamePlayer.guiItems.gameWorld.GameWorld;

public interface GameWorldListener {
	public void makeTower(double x, double y);

	void registerGameWorld(GameWorld world);
}
