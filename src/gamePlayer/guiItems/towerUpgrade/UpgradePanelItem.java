package gamePlayer.guiItems.towerUpgrade;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiItemsListeners.UpgradeListener;

public interface UpgradePanelItem {

	public void setCurrentTower(TowerInfoObject tower);
}
