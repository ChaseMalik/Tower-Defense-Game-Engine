package utilities.JavaFXutilities.imageView;

import gameAuthoring.mainclasses.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import utilities.errorPopup.ErrorPopup;

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
            new ErrorPopup(Constants.FILE_NOT_LOADED);
        }
        return imgView;
    }
}
