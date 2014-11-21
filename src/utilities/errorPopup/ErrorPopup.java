package utilities.errorPopup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Utility class that easily allows for the creation of error popups in a new window.
 * @author Austin Kyker
 *
 */
public class ErrorPopup extends Stage {
    
    
    private static final String ERROR_FILE_LOC = "./src/utilities/errorPopup/error.PNG";
    private static final int ERROR_POPUP_WIDTH = 200;
    private static final int ERROR_POPUP_HEIGHT = 150;
    
    
    public ErrorPopup(String message){
        Group group = new Group();
//        group.getChildren().add(label);
        try {
            Image errorImg = new Image(new FileInputStream(new File(ERROR_FILE_LOC)), ERROR_POPUP_WIDTH,ERROR_POPUP_HEIGHT, false, true);
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ImageView imgView = new ImageView();
        group.getChildren().addAll(new Label(message));
        Scene scene = new Scene(group);
        this.setScene(scene);
        this.setResizable(false);
        this.show();
    }
}
