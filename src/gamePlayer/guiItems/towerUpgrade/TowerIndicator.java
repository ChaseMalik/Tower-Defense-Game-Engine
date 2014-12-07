package gamePlayer.guiItems.towerUpgrade;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TowerIndicator extends Circle {
	
	public static final double OPACITY = 0.5;

	public TowerIndicator(double x, double y, double r) {
		super(x, y, r, Color.ORANGE);
		this.setOpacity(OPACITY);
	}

}
