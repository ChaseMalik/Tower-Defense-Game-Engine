package utilities.JavaFXutilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Dimension2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 * 
 * @author Greg Lyons
 * 
 * Used to create a JavaFX Background from an image path and a size
 *
 */

public class BackgroundFromPath {

	public static Background get(String path, Dimension2D mySize) {
		Image background = null;
		try {
			background = new Image(new FileInputStream(new File(path)), mySize.getWidth(), mySize.getHeight(), false, false);
		} catch (FileNotFoundException e) {
		}
		Background b = new Background(
				new BackgroundImage(background,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
		return b;
	}

}
