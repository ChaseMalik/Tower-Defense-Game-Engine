package gamePlayer.guiItemsListeners;

import javafx.scene.image.ImageView;
import gamePlayer.guiItems.towerUpgrade.TowerIndicator;
import gamePlayer.guiItems.towerUpgrade.TowerUpgradePanel;

public interface UpgradeListener {

	public boolean upgradeTower(ImageView myTowerImageView, String upgradeName);
	public void registerUpgradePanel(TowerUpgradePanel upgradePanel);
	public void sellTower(ImageView myTowerImageView, TowerIndicator myIndicator);
}
