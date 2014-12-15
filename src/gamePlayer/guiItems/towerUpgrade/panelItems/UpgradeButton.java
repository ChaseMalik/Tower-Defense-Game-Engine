package gamePlayer.guiItems.towerUpgrade.panelItems;

import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiItemsListeners.UpgradeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;

public class UpgradeButton extends PanelButton implements UpgradePanelItem{

	public static final String UPGRADE_TEXT = "Upgrade to:" + "\n";
	public static final String COST_TEXT = "\n for ";

	public UpgradeButton(Color borderColor, Dimension2D size, String backgroundPath, EventHandler<ActionEvent> handler) {
		super(borderColor, size, backgroundPath);
		this.setOnAction(event -> handler.handle(event));
	}


	@Override
	public void setCurrentTower(TowerInfoObject tower) {
		this.setText(UPGRADE_TEXT + tower.getMyUpgrade().getName()
				   + COST_TEXT + tower.getMyUpgrade().getBuyCost());

	}
	

}
