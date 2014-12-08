package gameAuthoring.scenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.mainclasses.Constants;
import gameAuthoring.mainclasses.controllerInterfaces.GeneralSettingsConfiguring;
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


public class GeneralSettingScene {

    private static final String GENERAL_SETTINGS_IMG_PATH =
            "gameAuthoring/scenes/GeneralSettingsImage.png";
    private static final double FIELD_WIDTH = AuthorController.SCREEN_WIDTH / 4;
    private static final String MULTIPLAYER = "Coop";
    private static final String SINGLEPLAYER = "SinglePlayer";
    private static final String GENERAL_SETTING_MSG = "Create Your Game Settings";
    private GeneralSettingsConfiguring myGeneralSettingsController;
    private Scene myScene;
    private VBox myVBox;
    private TextField myNameTextField;
    private NumericalTextField myStartingHealthField;
    private NumericalTextField myStartingCashField;
    private ComboBox<String> myGameTypeComboBox;
    private Group root;

    public GeneralSettingScene (GeneralSettingsConfiguring controller) {
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
        Label headingLabel = new Label(GENERAL_SETTING_MSG);
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
        createFieldWithLabel(Constants.NAME_OF_GAME, myNameTextField);
        createFieldWithLabel(Constants.STARTING_HEALTH, myStartingHealthField);
        createFieldWithLabel(Constants.STARTING_CASH, myStartingCashField);
        createFieldWithLabel(Constants.SELECT_GAME_TYPE, myGameTypeComboBox);
    }

    private void createFieldWithLabel (String labelName, Node node) {
        VBox vb = new VBox(Constants.MED_PADDING);
        Label optionLabel = new Label();
        optionLabel.textProperty().bind(MultiLanguageUtility.getInstance()
                .getStringProperty(labelName));
        vb.getChildren().addAll(optionLabel, node);
        myVBox.getChildren().add(vb);

    }

    private void createNextButton () {
        Button nextButton = new Button();
        nextButton.textProperty().bind(MultiLanguageUtility.getInstance()
                .getStringProperty(Constants.START_BUILDING));
        nextButton.setOnAction(event -> handleButtonClick());
        myVBox.getChildren().add(nextButton);
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
