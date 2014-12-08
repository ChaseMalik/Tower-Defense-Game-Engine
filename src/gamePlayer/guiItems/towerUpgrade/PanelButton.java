package gamePlayer.guiItems.towerUpgrade;

import javafx.geometry.Dimension2D;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PanelButton extends Button{

	public PanelButton(Color borderColor, Dimension2D size) {
		this.setBorder(StandardBorder.get(borderColor));
		this.setPrefSize(size.getWidth(), size.getHeight());
		this.setFont(Font.font("sans-serif", FontWeight.BOLD, 16.0));
		this.setStyle("-fx-text-alignment: center");
	}

}
