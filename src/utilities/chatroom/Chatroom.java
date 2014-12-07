package utilities.chatroom;

import utilities.networking.HTTPConnection;
import gameAuthoring.mainclasses.AuthorController;
import gameEngine.CoOpManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Utility class that easily allows for the creation of error popups in a new window.
 * @author Austin Kyker
 *
 */
public class Chatroom extends Stage {
    
    private static final String SERVER_URL = "https://voogasalad.herokuapp.com/";
    public static final HTTPConnection HTTP_CONNECTOR = new HTTPConnection(SERVER_URL);
        
    private static final double HEIGHT = AuthorController.SCREEN_HEIGHT/2;
    private static final double WIDTH = 400;
    private static final String TITLE = "Chatroom";
    private TextField myTextField;
    private ScrollPane myScrollPane;
    private Timeline myTimeline;
    
    
    public Chatroom(){
        VBox container = new VBox(10);
        myScrollPane = new ScrollPane();
        myScrollPane.setPrefSize(WIDTH, HEIGHT);
        container.setAlignment(Pos.CENTER);
        myTextField = new TextField();
        myTextField.setOnKeyPressed(event->handleKeyPressed(event));
        container.getChildren().addAll(myScrollPane, myTextField);
        this.setScene(new Scene(container));
        this.setTitle(TITLE);
        this.setResizable(false);
        this.show();
    }
   

    private void handleKeyPressed (KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER && !myTextField.getText().isEmpty()) {
            sendMessage(myTextField.getText());
        }
    }


    public void sendMessage(String message) {
        HTTP_CONNECTOR.sendPost("post_message", "message=" + message);
        myTextField.setText("");
    }
}