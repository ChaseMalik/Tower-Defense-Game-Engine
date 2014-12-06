package gamePlayer.Listeners;

import javafx.scene.image.ImageView;
import gamePlayer.guiItems.towerUpgrade.TowerUpgradePanel;

public interface UpgradeListener {

	public void upgradeTower(ImageView myTowerImageView, String upgradeName);
	public void registerUpgradePanel(TowerUpgradePanel upgradePanel);
	public void sellTower(ImageView myTowerImageView);
}
