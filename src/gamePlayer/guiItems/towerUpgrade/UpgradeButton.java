package gamePlayer.guiItems.towerUpgrade;

import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiItemsListeners.UpgradeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;

public class UpgradeButton extends PanelButton implements UpgradePanelItem{
	
	private EventHandler<ActionEvent> myHandler;

	public static final String UPGRADE_TEXT = "Upgrade to:" + "\n";

	public UpgradeButton(Color borderColor, Dimension2D size, String backgroundPath, EventHandler<ActionEvent> handler) {
		super(borderColor, size, backgroundPath);
		myHandler = handler;
		
	}


	@Override
	public void setCurrentTower(TowerInfoObject tower) {
		this.setText(UPGRADE_TEXT + tower.getMyUpgrade().getName());
		this.setOnAction(event -> myHandler.handle(event));
	}
	

}
