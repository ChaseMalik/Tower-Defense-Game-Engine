package gamePlayer.guiItems.towerUpgrade;

import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiItemsListeners.UpgradeListener;
import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;

public class UpgradeButton extends PanelButton implements UpgradePanelItem{

	public static final String UPGRADE_TEXT = "Upgrade to:" + "\n";

	public UpgradeButton(Color borderColor, Dimension2D size, String backgroundPath) {
		super(borderColor, size, backgroundPath);
	}


	@Override
	public void setCurrentTower(TowerInfoObject tower, UpgradeListener listener) {
		this.setText(UPGRADE_TEXT + tower.getMyUpgrade().getName());
		this.setOnAction(event -> listener.upgradeTower());
	}
	

}
