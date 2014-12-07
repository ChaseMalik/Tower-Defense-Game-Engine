package gameAuthoring.scenes;

import utilities.JavaFXutilities.DragAndDropFilePanes.audioPanes.DragAndDropCopyAudioPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.mainclasses.controllerInterfaces.GameDirectoryBuilding;
import gameAuthoring.mainclasses.controllerInterfaces.GeneralSettingsConfiguring;

public class GeneralSettingScene {

	private static final String GENERAL_SETTING_MSG = "Create Your Game Settings";
	private GeneralSettingsConfiguring myGameDirectoryController;
	private Scene myScene;
	private VBox myVBox;
	private TextField myNameTextField;
	private TextField myHealthTextField;
	private TextField myCashTextField;
	private DragAndDropCopyAudioPane myAudioPane;



	public GeneralSettingScene(){
		//myGameDirectoryController = generalSettingsConfiguring;
		Group root = new Group();
		myScene = new Scene(root, AuthorController.SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT);
		myVBox = new VBox(25);
		myVBox.setPadding(new Insets(50));
		myVBox.setAlignment(Pos.CENTER);
		root.getChildren().add(myVBox);
		createHeadingLabel();
	
		createTextFields();
		createOptions();
		myAudioPane = new DragAndDropCopyAudioPane(200, 200, "/src");	
		//createAudioPane("Drag Your Desired Background Music Here:", myAudioPane);
		createNextButton();
	}


	private void createHeadingLabel(){
		Label headingLabel = new Label(GENERAL_SETTING_MSG);
		headingLabel.setStyle("-fx-font-size: 32px");
		myVBox.getChildren().add(headingLabel);
	}


	private void createTextFields(){
		myNameTextField = new TextField();
		myHealthTextField = new TextField();
		myCashTextField = new TextField();		
	}

	private void createOptions(){
		createTextFieldWithLabel("Name of Your Game", myNameTextField);
		createTextFieldWithLabel("Starting Health", myHealthTextField);
		createTextFieldWithLabel("Starting Amount of Cash", myCashTextField);
	}


	private void createTextFieldWithLabel(String labelName, TextField textField){
		VBox vb = new VBox(15);
		//vb.setAlignment(Pos.BOTTOM_LEFT);
		Label optionLabel = new Label(labelName);
		vb.getChildren().addAll(optionLabel, textField);
		myVBox.getChildren().add(vb);

	}


	private void createAudioPane(String audioPaneLabel, DragAndDropCopyAudioPane audioPane){

		Label audioLabel = new Label(audioPaneLabel);
		VBox vb = new VBox(15);
		//vb.getChildren().add(audioPane);
	
	}


	private void createNextButton(){

		Button nextButton = new Button("Start Building!");
		nextButton.setOnAction(event -> handleButtonClick());	
		
		myVBox.getChildren().add(nextButton);	
	}

	private void handleButtonClick(){
		
		//THIS IS TEMPORARY
		System.out.println(myNameTextField.getText());
		System.out.println(myHealthTextField.getText());
		System.out.println(myCashTextField.getText());
	}




	public Scene getScene(){
		return myScene;
	}






}
