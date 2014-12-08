package gamePlayer.mainClasses.welcomeScreen;

import gamePlayer.guiFeatures.LMController;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.guiBuilder.GuiText;
import gamePlayer.mainClasses.managers.GuiManager;
import gamePlayer.mainClasses.welcomeScreen.availableGames.GameChooser;
import gamePlayer.mainClasses.welcomeScreen.startingOptions.MultiPlayerOptions;
import gamePlayer.mainClasses.welcomeScreen.startingOptions.PlayerCountOptions;

import java.io.File;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.XMLParsing.XMLParser;
import utilities.multilanguage.MultiLanguageUtility;

public class GameStartManager {
    private Stage myStage;
    public static final String propertiesPath = "./src/gamePlayer/properties/WelcomeScreenProperties.XML";
    private XMLParser parser;
    private WelcomeScreen welcomeScreen;
    private String gameTypeBeingChosen;
    private Group group;
    private PlayerCountOptions playerCountOptions;

    public GameStartManager(Stage stage) {
        myStage = stage;
        parser = new XMLParser(new File(propertiesPath));

        MultiLanguageUtility util = MultiLanguageUtility.getInstance();
        util.initLanguages("properties.gamePlayer.languages.English.properties",
                           "properties.gamePlayer.languages.Swahili.properties");

        util.setLanguage("English");

        GuiConstants.MULTILANGUAGE = util;
        GuiConstants.GAME_START_MANAGER = this;
    }


    public void init() {
    	GuiConstants.DYNAMIC_SIZING = true;
        group  = new Group();
        Scene scene = new Scene(group,GuiConstants.WINDOW_WIDTH,GuiConstants.WINDOW_HEIGHT);

        String styleSheetPath = parser.getValuesFromTag("StyleSheet").get(0);
        scene.getStylesheets().add(this.getClass().getResource(styleSheetPath).toExternalForm());

        initializeWelcomeScreen(group);
        addLMButton();
        
        myStage.setScene(scene);
        myStage.setResizable(false);
        myStage.show();
    }

    private void initializeWelcomeScreen (Group group) {
        welcomeScreen = new WelcomeScreen();
        welcomeScreen.setBackgroundImage(getImageFromPath(parser.getValuesFromTag("BackgroundImage").get(0),WelcomeScreen.WIDTH,WelcomeScreen.HEIGHT));

        welcomeScreen.setTopContent(getImageFromPath(parser.getValuesFromTag("Logo").get(0),WelcomeScreen.PANE_WIDTH,WelcomeScreen.PANE_HEIGHT/2));

        playerCountOptions = new PlayerCountOptions();
        playerCountOptions.getSinglePlayerOption().setOnMouseReleased(event->startSinglePlayerGameChooser());
        playerCountOptions.getMultiPlayerOption().setOnMouseReleased(event->startMultiPlayerOptions());
        welcomeScreen.setCenterContent(playerCountOptions); 

        welcomeScreen.setBottomContent(createLanguageSelector());
        
        group.getChildren().add(welcomeScreen);
    }

    private ComboBox createLanguageSelector() {
        ComboBox<String> languageSelector = new ComboBox<>();
        languageSelector.setLayoutX(100);
        languageSelector.itemsProperty().bind(GuiConstants.MULTILANGUAGE.getSupportedLanguages());
        languageSelector.getSelectionModel().select(GuiConstants.MULTILANGUAGE.getCurrentLanguage());
        languageSelector.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {
            @Override
            public void changed (ObservableValue<? extends String> arg0,
                                 String oldVal,
                                 String newVal) {
                try {
                    GuiConstants.MULTILANGUAGE.setLanguage(newVal);
                }
                catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        });
        return languageSelector;
    }

    public void joinMultiPlayerGame() {
        GuiManager manager = new GuiManager(myStage);
        manager.joinMultiPlayerGame();
    }

    private void startSinglePlayerGameChooser() {
        gameTypeBeingChosen = GuiConstants.SINGLE_PLAYER_GAME;
        GameChooser chooser = new GameChooser(GuiConstants.SINGLE_PLAYER_GAMES_DIRECTORY);
        welcomeScreen.swipeForward(chooser);
    }

    private void startMultiPlayerGameChooser() {
        gameTypeBeingChosen = GuiConstants.MULTI_PLAYER_GAME;
        GameChooser chooser = new GameChooser(GuiConstants.MULTI_PLAYER_GAMES_DIRECTORY);
        welcomeScreen.swipeForward(chooser);
    }

    private void startMultiPlayerOptions() {
        MultiPlayerOptions multiPlayerOptions = new MultiPlayerOptions();
        multiPlayerOptions.getNewGameOption().setOnMouseReleased(event->startMultiPlayerGameChooser());
        multiPlayerOptions.getJoinGameOption().setOnMouseReleased(event->joinMultiPlayerGame());
        welcomeScreen.swipeForward(multiPlayerOptions);
    }

    public void startSinglePlayerGame(String directoryPath) {
        GuiManager manager = new GuiManager(myStage);
        GuiConstants.GUI_MANAGER.init();
        manager.startSinglePlayerGame(directoryPath);
    }

    private void startMultiPlayerGame(String directoryPath) {
        //wait for other player to join
        welcomeScreen.setCenterContent(new LoadingIndicator(GuiConstants.MULTILANGUAGE.getStringProperty(GuiText.WAITING_FOR_CHALLENGER)));

        GuiManager manager = new GuiManager(myStage);
        manager.prepareMultiPlayerGame(directoryPath);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> pollEngineForMultiPlayerReadiness(manager,timeline)));
        timeline.play();
    }

    private void pollEngineForMultiPlayerReadiness(GuiManager manager,Timeline timeline) {
        if (manager.multiPlayerGameIsReady()) {
            timeline.stop();
            manager.startMultiPlayerGame();
        }
    }

    private ImageView getImageFromPath(String imagePath,double width,double height) {
        Image image = new Image(imagePath,width,height,false,true);
        return new ImageView(image);
    }

    public void startGame (File file) {

    	LMController.getInstance().clearAllHandlers();

        if (gameTypeBeingChosen.equals(GuiConstants.SINGLE_PLAYER_GAME)) {
            startSinglePlayerGame(file.getPath());
        } else if (gameTypeBeingChosen.equals(GuiConstants.MULTI_PLAYER_GAME)) {
            startMultiPlayerGame(file.getPath());
        }
    }
    
    private void addLMButton() {
    	LMConnector connector = new LMConnector();
    	LMController.getInstance().onConnect(event -> addLMActions());
    	Dimension2D size = new Dimension2D(GuiConstants.BOTTOM_CONTAINER_WIDTH, GuiConstants.BOTTOM_CONTAINER_HEIGHT);
    	connector.initialize(size);
    	HBox hbox = new HBox();
    	hbox.setPrefSize(size.getWidth(), size.getHeight());
    	hbox.getChildren().add(connector.getNode());
    	hbox.setAlignment(Pos.CENTER);
    	hbox.setTranslateY(550);
    	group.getChildren().add(hbox);
    }
    
    private void addLMActions() {
    	LMController controller = LMController.getInstance();
    	controller.onSwipeRight(event -> navigateBack());
    }
    
    private void navigateBack() {
    	welcomeScreen.swipeBack(playerCountOptions);
    }
}
