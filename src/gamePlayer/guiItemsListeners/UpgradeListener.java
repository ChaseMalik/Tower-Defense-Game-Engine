package gamePlayer.guiItemsListeners;

import javafx.scene.Node;
import gamePlayer.guiItems.towerUpgrade.TowerUpgradePanel;

public interface UpgradeListener {

	public void upgradeTower(Node n);
	public void registerUpgradePanel(TowerUpgradePanel upgradePanel);
}
