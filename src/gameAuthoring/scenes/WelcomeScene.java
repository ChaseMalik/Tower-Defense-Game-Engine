package gameAuthoring.scenes;

import gameAuthoring.mainclasses.AuthorController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class WelcomeScene {
	
	private Scene myScene;
	public VBox myVBox;
	
	public WelcomeScene(){
		Group root = new Group();		
		myScene = new Scene(root, AuthorController.SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT);
		
		myVBox = new VBox(15);
		myVBox.setPrefWidth(AuthorController.SCREEN_WIDTH);
		myVBox.setPadding(new Insets(100,100,100,100));		
		createWelcomeLabel();
		createNameOption();
		//createStartButton();
		root.getChildren().add(myVBox);
		
	}
	
	
	private void createWelcomeLabel(){		
		Label welcomeLabel = new Label("Welcome To Our Tower Defense Game Engine");
		welcomeLabel.setFont(new Font(30.0));
		HBox welcomeLabelHBox = new HBox();
		welcomeLabelHBox.getChildren().add(welcomeLabel);
		welcomeLabelHBox.setAlignment(Pos.CENTER);
		myVBox.getChildren().add(welcomeLabelHBox);	
	}
	
	private void createNameOption(){		
		HBox nameOptionHBox = new HBox(15);
		Label nameLabel = new Label("Name of Game: ");
		TextField nameTextField = new TextField();
		nameOptionHBox.getChildren().addAll(nameLabel, nameTextField);	
		nameOptionHBox.setAlignment(Pos.CENTER);
		myVBox.getChildren().add(nameOptionHBox);
	}


	public Scene getScene(){
		return myScene;
	}

}
