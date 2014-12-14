package gamePlayer.guiItems.towerUpgrade;

import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;
import gameEngine.Data.TowerInfoObject;
import gamePlayer.guiItemsListeners.UpgradeListener;

public class SellButton extends PanelButton implements UpgradePanelItem {

	public SellButton(Color green, Dimension2D myButtonSize, String sellPath) {
		super(green, myButtonSize, sellPath);
	}

	@Override
	public void setCurrentTower(TowerInfoObject tower, UpgradeListener listener) {
		// TODO Auto-generated method stub
		
	}


}
