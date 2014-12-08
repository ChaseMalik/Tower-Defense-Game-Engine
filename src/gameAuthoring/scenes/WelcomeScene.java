package gameAuthoring.scenes;

import gameAuthoring.mainclasses.AuthorController;

import java.util.Observable;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import utilities.multilanguage.MultiLanguageUtility;
import utilities.multilanguage.languageexceptions.LanguageNotFoundException;


public class WelcomeScene extends Observable {

	private Scene myScene;
	private VBox myVBox;
	private Group root;
	private ComboBox<String> languageCB;
	private static final String SPANISH = "Spanish";
	private static final String ENGLISH = "English";


	public WelcomeScene (){

		root = new Group();
		myScene = new Scene(root, AuthorController.SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT);
		makeStartScreen();
		myVBox = new VBox(50);
		myVBox.setPrefWidth(AuthorController.SCREEN_WIDTH);
		myVBox.setPadding(new Insets(200));
		myVBox.setAlignment(Pos.CENTER);
		root.getChildren().add(myVBox);
		createWelcomeLabel();
		//MultiLanguageUtility.getInstance().setLanguage(languageCB.getValue());
//		myScene.setOnMouseClicked(event -> {
//			if(!languageCB.getValue().isEmpty()){
//			this.setChanged();
//			this.notifyObservers();
//			}
//		});

	}

	private void createLanguageComboBox() {
		VBox languageOptionVB = new VBox(15);
		languageOptionVB.setAlignment(Pos.CENTER);
		Label languageLabel = new Label("Choose Your Language");
		languageLabel.setStyle("-fx-font-size: 24px");
		languageCB = new ComboBox<String>();
		languageCB.getItems().addAll(
				ENGLISH,SPANISH				
				);
		languageCB.setOnAction(event ->{
			
			try {
				MultiLanguageUtility.getInstance().setLanguage(languageCB.getValue());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.setChanged();
			this.notifyObservers();
			
		});
		languageOptionVB.getChildren().addAll(languageLabel, languageCB);
		myVBox.getChildren().add(languageOptionVB);
		
	}

	private void createWelcomeLabel () {
		Label welcomeLabel = new Label();
		welcomeLabel.textProperty().bind(MultiLanguageUtility.getInstance().getStringProperty("WelcomeMessage"));
		welcomeLabel.setStyle("-fx-font-size: 32px");
		myVBox.getChildren().addAll(welcomeLabel);
		createLanguageComboBox();
	}

	public void makeStartScreen(){
		Image img = new Image("gameAuthoring/scenes/WelcomeSplash.png");
		ImageView imgView = new ImageView(img);
		root.getChildren().add(imgView);
	}


	public Scene getScene () {
		return myScene;
	}

}
