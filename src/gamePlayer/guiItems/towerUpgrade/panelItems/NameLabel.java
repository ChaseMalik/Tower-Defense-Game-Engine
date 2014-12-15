package gamePlayer.guiItems.towerUpgrade.panelItems;

import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiItems.towerUpgrade.UpgradeConstants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;

public class NameLabel extends BackgroundLabel implements UpgradePanelItem {

	public NameLabel(Dimension2D labelSize) {
		super("", labelSize, UpgradeConstants.STONE_PATH);
		this.wrapTextProperty().set(true);
	}

	@Override
	public void setCurrentTower(TowerInfoObject tower) {
		this.setText(tower.getName());
	}
	
}
