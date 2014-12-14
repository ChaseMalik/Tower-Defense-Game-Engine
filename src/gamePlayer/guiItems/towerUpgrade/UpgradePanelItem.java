package gamePlayer.guiItems.towerUpgrade;

import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiItemsListeners.UpgradeListener;

public interface UpgradePanelItem {

	public void setCurrentTower(TowerInfoObject tower, UpgradeListener listener);
}
