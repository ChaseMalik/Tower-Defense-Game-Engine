package gameAuthoring.scenes;

import gameAuthoring.mainclasses.AuthorController;
import java.util.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WelcomeScene extends Observable {

    private static final String WELCOME_MSG = "Welcome to our Authoring Environment";
    private Scene myScene;
    private VBox myVBox;
    private TextField nameTextField;

    public WelcomeScene(){
        Group root = new Group();		
        myScene = new Scene(root, AuthorController.SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT);
        myVBox = new VBox(20);
        myVBox.setPrefWidth(AuthorController.SCREEN_WIDTH);
        myVBox.setPadding(new Insets(200));
        myVBox.setAlignment(Pos.CENTER);
        root.getChildren().add(myVBox);
        createWelcomeLabel();
        createGameNameField();
    }

    private void createWelcomeLabel(){		
        Label welcomeLabel = new Label(WELCOME_MSG);
        welcomeLabel.setStyle("-fx-font-size: 32px");
        myVBox.getChildren().add(welcomeLabel);	
    }

    private void createGameNameField(){		
        HBox nameOptionHBox = new HBox(15);
        Label nameLabel = new Label("Name of Game: ");
        nameLabel.setStyle("-fx-font-size: 16px");
        nameTextField = new TextField();
        nameOptionHBox.getChildren().addAll(nameLabel, nameTextField, createStartButton());	
        nameOptionHBox.setAlignment(Pos.CENTER);
        myVBox.getChildren().add(nameOptionHBox);
    }
    
    private Button createStartButton(){
        Button startButton = new Button("Start Building!");
        startButton.setOnAction(event->handleButtonClick());
        return startButton;
    }
    
    private void handleButtonClick () {
        if(!nameTextField.getText().isEmpty()) {
            WelcomeScene.this.setChanged();
            WelcomeScene.this.notifyObservers(nameTextField.getText());
        }
    }

    public Scene getScene(){
        return myScene;
    }

}
