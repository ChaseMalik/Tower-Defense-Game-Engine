package gameAuthoring.scenes;

import utilities.multilanguage.MultiLanguageUtility;
import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.mainclasses.controllerInterfaces.GameDirectoryBuilding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class WelcomeScene {

    private Scene myScene;
    private VBox myVBox;
    private TextField nameTextField;
    private GameDirectoryBuilding myGameDirectoryController;

    public WelcomeScene (GameDirectoryBuilding controller) {
        myGameDirectoryController = controller;
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

    private void createWelcomeLabel () {
        Label welcomeLabel = new Label();
        welcomeLabel.textProperty().bind(MultiLanguageUtility.getInstance().getStringProperty("WelcomeMessage"));
        welcomeLabel.setStyle("-fx-font-size: 32px");
        myVBox.getChildren().add(welcomeLabel);
    }

    private void createGameNameField () {
        HBox nameOptionHBox = new HBox(15);
        Label nameLabel = new Label();
        nameLabel.textProperty().bind(MultiLanguageUtility.getInstance().getStringProperty("NameOfGamePrompt"));
        nameLabel.setStyle("-fx-font-size: 16px");
        nameTextField = new TextField();
        nameOptionHBox.getChildren().addAll(nameLabel, nameTextField, createStartButton());
        nameOptionHBox.setAlignment(Pos.CENTER);
        myVBox.getChildren().add(nameOptionHBox);
    }

    private Button createStartButton () {
        Button startButton = new Button();
        startButton.textProperty().bind(MultiLanguageUtility.getInstance().getStringProperty("StartBuild"));
        startButton.setOnAction(event -> handleButtonClick());
        return startButton;
    }

    private void handleButtonClick () {
        String gameNameText = nameTextField.getText();
        if (!gameNameText.isEmpty()) {
            myGameDirectoryController.makeDirectory(gameNameText);
        }
    }

    public Scene getScene () {
        return myScene;
    }

}
