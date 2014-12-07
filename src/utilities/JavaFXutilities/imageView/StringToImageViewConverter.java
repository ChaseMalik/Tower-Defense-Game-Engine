package utilities.JavaFXutilities.imageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import utilities.errorPopup.ErrorPopup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The purpose of this class is to allow the user to get an image view from a string. This
 * will be used throughout AuthoringEnvironment and may also appear in the GameEngine and 
 * front-end Player
 * @author Austin Kyker
 *
 */
public class StringToImageViewConverter {
    
    public static CenteredImageView getImageView(double width, double height, String path) {
        CenteredImageView imgView = new CenteredImageView();
        try {
            Image image;
            image = new Image(new FileInputStream(new File(path.replace("\\", "/"))), width, height, false, false);
            imgView.setImage(image);
            imgView.setFitWidth(width);
            imgView.setFitHeight(height);
        }
        catch (FileNotFoundException e) {
            new ErrorPopup("File " + path + " could not be loaded.");
        }
        return imgView;
    }
}
