package gamePlayer.guiItems.towerUpgrade;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class StandardBorder {
	
	public static Border get(Color c){
		return new Border(new BorderStroke(c, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THICK));
	}
	
}
