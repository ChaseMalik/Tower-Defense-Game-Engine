package gameAuthoring.scenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.mainclasses.Constants;
import gameAuthoring.mainclasses.controllerInterfaces.IGeneralSettingsConfiguring;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utilities.GSON.objectWrappers.GeneralSettingsWrapper;
import utilities.JavaFXutilities.numericalTextFields.NumericalTextField;
import utilities.multilanguage.MultiLanguageUtility;

/**
 * Scene for user to input their general game settings (Name, Health, Money, GameType)
 * @author David Zhang, Austin Kyker
 *
 */
public class GeneralSettingScene {

    private static final int BTN_SPACE = 140;
    private static final String GENERAL_SETTINGS_IMG_PATH =
            "gameAuthoring/scenes/GeneralSettingsImage.png";
    private static final double FIELD_WIDTH = AuthorController.SCREEN_WIDTH / 4;
    private static final String MULTIPLAYER = "Coop";
    private static final String SINGLEPLAYER = "SinglePlayer";
    private IGeneralSettingsConfiguring myGeneralSettingsController;
    private Scene myScene;
    private VBox myVBox;
    private TextField myNameTextField;
    private NumericalTextField myStartingHealthField;
    private NumericalTextField myStartingCashField;
    private ComboBox<String> myGameTypeComboBox;
    private Group root;

    public GeneralSettingScene (IGeneralSettingsConfiguring controller) {
        myGeneralSettingsController = controller;
        root = new Group();
        myScene = new Scene(root, AuthorController.SCREEN_WIDTH,
                            AuthorController.SCREEN_HEIGHT);
        myVBox = new VBox(25);
        myVBox.setPrefWidth(AuthorController.SCREEN_WIDTH / 2);
        myVBox.setPadding(new Insets(100));
        myVBox.setAlignment(Pos.CENTER);

        createHeadingLabel();
        createOptionFields();
        createOptions();
        createNextButton();
        createRightImage();
    }

    private void createHeadingLabel () {
        Label headingLabel = new Label();
        headingLabel.textProperty().bind(MultiLanguageUtility.getInstance()
                                         .getStringProperty(Constants.GENERAL_SETTING_MSG));
        headingLabel.setStyle("-fx-font-size: 24px");
        myVBox.getChildren().add(headingLabel);
    }

    private void createOptionFields () {
        myNameTextField = new TextField();
        myNameTextField.setPrefWidth(AuthorController.SCREEN_WIDTH / 3);
        myStartingHealthField = new NumericalTextField(FIELD_WIDTH);
        myStartingCashField = new NumericalTextField(FIELD_WIDTH);
        myGameTypeComboBox = new ComboBox<String>();
        myGameTypeComboBox.getItems().addAll(SINGLEPLAYER, MULTIPLAYER);
    }

    private void createOptions () {
        myVBox.getChildren().addAll(createFieldWithLabel(Constants.NAME_OF_GAME, 
                                                         myNameTextField),
                                    createFieldWithLabel(Constants.STARTING_HEALTH,
                                                         myStartingHealthField),
                                    createFieldWithLabel(Constants.STARTING_CASH,
                                                         myStartingCashField));
        HBox box = new HBox(BTN_SPACE);
        box.getChildren().addAll(createFieldWithLabel(Constants.SELECT_GAME_TYPE,
                                                      myGameTypeComboBox),
                                 createNextButton());
        myVBox.getChildren().add(box);
    }

    private VBox createFieldWithLabel (String labelName, Node node) {
        VBox box = new VBox(Constants.MED_PADDING);
        Label optionLabel = new Label();
        optionLabel.textProperty().bind(MultiLanguageUtility.getInstance()
                .getStringProperty(labelName));
        box.getChildren().addAll(optionLabel, node);
        return box;
    }

    private VBox createNextButton () {
        VBox box = new VBox(Constants.MED_PADDING);
        Button nextButton = new Button();
        nextButton.textProperty().bind(MultiLanguageUtility.getInstance()
                .getStringProperty(Constants.START_BUILDING));
        nextButton.setOnAction(event -> handleButtonClick());
        box.getChildren().addAll(new Label(""), nextButton);
        return box;
    }

    private void handleButtonClick () {
        String gameNameText = myNameTextField.getText();
        String gameType = myGameTypeComboBox.getValue();
        if (!gameNameText.isEmpty() && !gameType.isEmpty() &&
            myStartingCashField.isValueEntered() && myStartingHealthField.isValueEntered()) {
            myGeneralSettingsController.makeDirectory(gameNameText, gameType);
            GeneralSettingsWrapper wrapper =
                    new GeneralSettingsWrapper(myStartingHealthField.getNumber(),
                                               myStartingCashField.getNumber());
            myGeneralSettingsController.setGeneralSettings(wrapper);
        }
    }

    public void createRightImage () {
        HBox hb = new HBox();
        Image img = new Image(GENERAL_SETTINGS_IMG_PATH);
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(AuthorController.SCREEN_WIDTH / 2);
        imgView.setFitHeight(AuthorController.SCREEN_HEIGHT);
        hb.getChildren().addAll(myVBox, imgView);
        root.getChildren().add(hb);
    }

    public Scene getScene () {
        return myScene;
    }
}
