package gameAuthoring.scenes;

import gameAuthoring.mainclasses.AuthorController;

import java.util.Observable;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import utilities.multilanguage.MultiLanguageUtility;


public class WelcomeScene extends Observable {

    private Scene myScene;
    private VBox myVBox;
    

    public WelcomeScene () {

        Group root = new Group();
        myScene = new Scene(root, AuthorController.SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT);
        myVBox = new VBox(20);
        myVBox.setPrefWidth(AuthorController.SCREEN_WIDTH);
        myVBox.setPadding(new Insets(200));
        myVBox.setAlignment(Pos.CENTER);
        root.getChildren().add(myVBox);
        createWelcomeLabel();
        myScene.setOnMouseClicked(event -> {
        	this.setChanged();
        	this.notifyObservers();
        });
        
    }

    private void createWelcomeLabel () {
        Label welcomeLabel = new Label();
        welcomeLabel.textProperty().bind(MultiLanguageUtility.getInstance().getStringProperty("WelcomeMessage"));
        welcomeLabel.setStyle("-fx-font-size: 32px");
        Label clickAnywhere = new Label("Click Anywhere to Begin!");
        clickAnywhere.setStyle("-fx-font-size:16px");
        myVBox.getChildren().addAll(welcomeLabel,clickAnywhere);
    }

    public Scene getScene () {
        return myScene;
    }

}
