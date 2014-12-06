package utilities.errorPopup;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utilities.JavaFXutilities.StringToImageViewConverter;

/**
 * Utility class that easily allows for the creation of error popups in a new window.
 * @author Austin Kyker
 *
 */
public class ErrorPopup extends Stage {
        
    private static final String ERROR_FILE_LOC = "./src/utilities/errorPopup/error.PNG";
    private static final int ERROR_POPUP_WIDTH = 260;
    private static final int ERROR_POPUP_HEIGHT = 225;
    private static final String TITLE = "Error";
    
    
    public ErrorPopup(String message){
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        ImageView imgView = StringToImageViewConverter.getImageView(ERROR_POPUP_WIDTH, 
                                                                    ERROR_POPUP_HEIGHT, 
                                                                    ERROR_FILE_LOC);
        Label label = new Label(message);
        label.setPrefWidth(ERROR_POPUP_WIDTH);
        label.setWrapText(true);
        box.getChildren().addAll(label, imgView);
        this.setScene(new Scene(box));
        this.setTitle(TITLE);
        this.setResizable(false);
        this.show();
    }   
}