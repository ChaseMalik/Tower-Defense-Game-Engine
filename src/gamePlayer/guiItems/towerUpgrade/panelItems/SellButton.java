// This entire file is part of my masterpiece.
// GREG LYONS

package gamePlayer.guiItems.towerUpgrade.panelItems;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiItems.towerUpgrade.UpgradeConstants;

public class SellButton extends PanelButton implements UpgradePanelItem {

	public SellButton(Dimension2D myButtonSize, EventHandler<ActionEvent> handler) {
		super(UpgradeConstants.SELL_COLOR, myButtonSize, UpgradeConstants.SELL_PATH);
		this.setOnAction(event -> handler.handle(event));
	}

	@Override
	public void setCurrentTower(TowerInfoObject tower) {
		this.setText(UpgradeConstants.SELL_TEXT + tower.getSellCost());
	}

}
