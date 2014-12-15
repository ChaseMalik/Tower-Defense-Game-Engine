package gamePlayer.guiItems.towerUpgrade.panelItems;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;
import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiItemsListeners.UpgradeListener;

public class SellButton extends PanelButton implements UpgradePanelItem {

	public SellButton(Color green, Dimension2D myButtonSize, String sellPath, EventHandler<ActionEvent> handler) {
		super(green, myButtonSize, sellPath);
		this.setOnAction(event -> handler.handle(event));
	}

	@Override
	public void setCurrentTower(TowerInfoObject tower) {
		this.setText("Sell tower for:\n" + tower.getSellCost());

	}


}
