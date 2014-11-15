package utilities;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Utility class that easily allows for the creation of error popups in a new window.
 * @author Austin Kyker
 *
 */
public class ErrorPopup extends Stage {

    private static final int ERROR_POPUP_WIDTH = 200;
    private static final int ERROR_POPUP_HEIGHT = 200;
    
    public ErrorPopup(String message){
        Group group = new Group();
        group.getChildren().add(new Label(message));
        Scene scene = new Scene(group, ERROR_POPUP_WIDTH, ERROR_POPUP_HEIGHT);
        this.setScene(scene);
        this.setResizable(false);
        this.show();
    }
}
