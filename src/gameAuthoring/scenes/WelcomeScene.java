package gameAuthoring.scenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.mainclasses.Constants;
import java.util.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import utilities.multilanguage.MultiLanguageUtility;

/**
 * A welcome scene for the user to choose their language of preference.
 * @author Austin Kyker, David Zhang
 *
 */
public class WelcomeScene extends Observable {


    private static final int TOPDOWN_PADDING = 200;
    private static final int BOX_SPACING = 50;
    private static final String CHOOSE_LANG = "Choose Your Language";
    private Scene myScene;
    private VBox myVBox;
    private Group root;
    private ComboBox<String> myLanguageCB;
    private static final String CHINESE = "Chinese";
    private static final String ENGLISH = "English";
    private static final String SPANISH = "Spanish";

    public WelcomeScene () {
        root = new Group();
        myScene = new Scene(root, AuthorController.SCREEN_WIDTH, 
                            AuthorController.SCREEN_HEIGHT);
        myVBox = new VBox(BOX_SPACING);
        myVBox.setPrefWidth(AuthorController.SCREEN_WIDTH);
        myVBox.setPadding(new Insets(TOPDOWN_PADDING, 0, TOPDOWN_PADDING, 0));
        myVBox.setAlignment(Pos.CENTER);
        root.getChildren().add(myVBox);
        createWelcomeLabel();
    }

    private void createLanguageComboBox () {
        VBox languageOptionVB = new VBox(Constants.MED_PADDING);
        languageOptionVB.setAlignment(Pos.CENTER);
        Label languageLabel = new Label(CHOOSE_LANG);
        languageLabel.setStyle("-fx-font-size: 20px");
        myLanguageCB = new ComboBox<String>();
        myLanguageCB.getItems().addAll(ENGLISH, CHINESE, SPANISH);
        myLanguageCB.setOnAction(event -> {
            if (MultiLanguageUtility.getInstance().setLanguage(myLanguageCB.getValue())) {
                this.setChanged();
                this.notifyObservers();
            }
        });
        languageOptionVB.getChildren().addAll(languageLabel, myLanguageCB);
        myVBox.getChildren().add(languageOptionVB);
    }

    private void createWelcomeLabel () {
        Label welcomeLabel = new Label();
        welcomeLabel.textProperty().bind(MultiLanguageUtility.getInstance()
                .getStringProperty(Constants.WELCOME_MSG));
        welcomeLabel.setStyle("-fx-font-size: 44px");
        myVBox.getChildren().addAll(welcomeLabel);
        createLanguageComboBox();
    }

    public Scene getScene () {
        return myScene;
    }
}