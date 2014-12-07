package utilities.chatroom;

import gameAuthoring.mainclasses.AuthorController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.networking.HTTPConnection;

/**
 * Utility class that easily allows for the creation of error popups in a new window.
 * @author Austin Kyker
 *
 */
public class Chatroom extends Stage {

    private static final String NO_MSG_RESPONSE = "None";
    private static final String MSG_SEPERATOR = "~";
    private static final String SERVER_URL = "https://voogasalad.herokuapp.com/";
    public static final HTTPConnection HTTP_CONNECTOR = new HTTPConnection(SERVER_URL);

    private static final double HEIGHT = AuthorController.SCREEN_HEIGHT/2;
    private static final double WIDTH = 400;
    private static final String TITLE = "Chatroom";
    private TextField myTextField;
    private ScrollPane myScrollPane;
    private Timeline myRequestTimeline;
    private int myMessageIndex;
    private VBox myMessages;


    public Chatroom(){
        myMessageIndex = 0;
        setupChatroomGraphics();
        setupAndBeginMessageRequests();
    }

    private void setupChatroomGraphics () {
        VBox container = new VBox(10);
        myScrollPane = new ScrollPane();
        myScrollPane.setPrefSize(WIDTH, HEIGHT);
        myMessages = new VBox(10);
        myScrollPane.setContent(myMessages);
        container.setAlignment(Pos.CENTER);
        myTextField = new TextField();
        myTextField.setOnKeyPressed(event->handleKeyPressed(event));
        container.getChildren().addAll(myScrollPane, myTextField);
        this.setScene(new Scene(container));
        this.setTitle(TITLE);
        this.setResizable(false);
        this.show();
    }

    public void setupAndBeginMessageRequests() {
        myRequestTimeline = new Timeline();
        myRequestTimeline.setCycleCount(Animation.INDEFINITE);
        myRequestTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2),
                                                          event -> pollServerForMessages()));
        myRequestTimeline.play();
    }


    private void pollServerForMessages () {
        String messageResponse = HTTP_CONNECTOR.sendGet("get_messages/" + myMessageIndex);
        if(!messageResponse.equals(NO_MSG_RESPONSE)) {
            myMessageIndex = Integer.parseInt(messageResponse.substring(0, messageResponse.indexOf("~")));
            String[] messages = messageResponse.substring(messageResponse.indexOf("~")).trim().split(MSG_SEPERATOR);
            for(String msg:messages){
                if(!msg.isEmpty())
                    myMessages.getChildren().add(new Label(msg));
            }
        }

        myScrollPane.setVvalue(1.0);
    }

    private void handleKeyPressed (KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER && !myTextField.getText().isEmpty()) {
            sendMessage(myTextField.getText());
        }
    }


    public void sendMessage(String message) {
        myMessageIndex = Integer.parseInt(HTTP_CONNECTOR.sendPost("post_message", "message=" + message));
        myMessages.getChildren().add(new Label(message));
        myTextField.setText("");

        myScrollPane.setVvalue(1.0);
    }
}