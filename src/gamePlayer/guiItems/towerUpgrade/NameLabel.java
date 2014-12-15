package gamePlayer.guiItems.towerUpgrade;

import gameEngine.Data.TowerInfoObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;

public class NameLabel extends BackgroundLabel implements UpgradePanelItem {

	private EventHandler<ActionEvent> myHandler;

	public NameLabel(Dimension2D labelSize, String imagePath, EventHandler<ActionEvent> handler) {
		super("", labelSize, imagePath);
		myHandler = handler;
	}

	@Override
	public void setCurrentTower(TowerInfoObject tower) {
		this.setText(tower.getName());
	}
	
}
