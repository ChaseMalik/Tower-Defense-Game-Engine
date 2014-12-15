package gamePlayer.guiItems.towerUpgrade.panelItems;

import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiItems.towerUpgrade.UpgradeConstants;
import gamePlayer.guiItemsListeners.UpgradeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;

public class UpgradeButton extends PanelButton implements UpgradePanelItem{

	public UpgradeButton(Dimension2D size, EventHandler<ActionEvent> handler) {
		super(Color.ORANGERED, size, UpgradeConstants.UP_PATH);
		this.setOnAction(event -> handler.handle(event));
	}

	@Override
	public void setCurrentTower(TowerInfoObject tower) {
		this.setText(UpgradeConstants.UPGRADE_TEXT + tower.getMyUpgrade().getName()
				   + UpgradeConstants.COST_TEXT + tower.getMyUpgrade().getBuyCost());

	}
	

}
