package gamePlayer.guiItems.towerUpgrade.panelItems;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import utilities.JavaFXutilities.BackgroundFromPath;
import utilities.JavaFXutilities.StandardBorder;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BackgroundLabel extends Label{

	public BackgroundLabel(String text, Dimension2D mySize, String backgroundPath) {
		super(text);
		this.setPrefSize(mySize.getWidth(), mySize.getHeight());
		this.setAlignment(Pos.CENTER);
		this.setFont(Font.font("sans-serif", FontWeight.BOLD, 20.0));
		this.setStyle("-fx-text-fill: WHITE;");
		this.setBackground(BackgroundFromPath.get(backgroundPath, mySize));
		this.setBorder(StandardBorder.get(Color.BLACK));
	}


}
