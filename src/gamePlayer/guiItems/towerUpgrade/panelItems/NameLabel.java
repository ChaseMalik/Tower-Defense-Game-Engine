package gamePlayer.guiItems.towerUpgrade.panelItems;

import gameEngine.Data.TowerInfoObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;

public class NameLabel extends BackgroundLabel implements UpgradePanelItem {

	public NameLabel(Dimension2D labelSize, String imagePath, EventHandler<ActionEvent> handler) {
		super("", labelSize, imagePath);
		this.wrapTextProperty().set(true);
	}

	@Override
	public void setCurrentTower(TowerInfoObject tower) {
		this.setText(tower.getName());
	}
	
}
