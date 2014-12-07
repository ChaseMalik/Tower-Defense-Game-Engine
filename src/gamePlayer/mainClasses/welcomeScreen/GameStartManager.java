package gamePlayer.mainClasses.welcomeScreen;

import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.managers.GuiManager;
import gamePlayer.mainClasses.welcomeScreen.availableGames.GameChooser;
import gamePlayer.mainClasses.welcomeScreen.startingOptions.MultiPlayerOptions;
import gamePlayer.mainClasses.welcomeScreen.startingOptions.PlayerCountOptions;
import java.io.File;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utilities.XMLParsing.XMLParser;
import utilities.textGenerator.TextGenerator;

public class GameStartManager {
    Stage myStage;
    public static final String propertiesPath = "./src/gamePlayer/properties/WelcomeScreenProperties.XML";
    private XMLParser parser;
    private WelcomeScreen welcomeScreen;

    public GameStartManager(Stage stage) {
        myStage = stage;
        parser = new XMLParser(new File(propertiesPath));
        GuiConstants.TEXT_GEN = new TextGenerator(parser.getValuesFromTag("TextGeneratorPropertiesPath").get(0));
        GuiConstants.GAME_START_MANAGER = this;
        init(myStage);
    }

    private void init(Stage stage) {
        Group group  = new Group();
        Scene scene = new Scene(group,GuiConstants.WINDOW_WIDTH,GuiConstants.WINDOW_HEIGHT);

        String styleSheetPath = parser.getValuesFromTag("StyleSheet").get(0);
        scene.getStylesheets().add(this.getClass().getResource(styleSheetPath).toExternalForm());

        initializeWelcomeScreen(group);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void initializeWelcomeScreen (Group group) {
        welcomeScreen = new WelcomeScreen();
        welcomeScreen.setBackgroundImage(getImageFromPath(parser.getValuesFromTag("BackgroundImage").get(0),WelcomeScreen.WIDTH,WelcomeScreen.HEIGHT));

        welcomeScreen.setTopContent(getImageFromPath(parser.getValuesFromTag("Logo").get(0),WelcomeScreen.PANE_WIDTH,WelcomeScreen.PANE_HEIGHT/2));
        
        PlayerCountOptions playerCountOptions = new PlayerCountOptions();
        playerCountOptions.getSinglePlayerOption().setOnMouseReleased(event->startGameChooser());
        playerCountOptions.getMultiPlayerOption().setOnMouseReleased(event->startMultiPlayerOptions());
        welcomeScreen.setCenterContent(playerCountOptions);

        //welcomeScreen.setBottomContent(new RotatingProgressIndicator());
        
        group.getChildren().add(welcomeScreen);
    }

    private void joinMultiPlayerGame() {
        //
    }
    
    private void startMultiPlayerGame() {
        //
    }
    
    private void startGameChooser() {
        GameChooser chooser = new GameChooser();
        welcomeScreen.setCenterContent(chooser);
    }
    
    private void startMultiPlayerOptions() {
        MultiPlayerOptions multiPlayerOptions = new MultiPlayerOptions();
        multiPlayerOptions.getNewGameOption().setOnMouseReleased(event->startMultiPlayerGame());
        multiPlayerOptions.getJoinGameOption().setOnMouseReleased(event->joinMultiPlayerGame());
        welcomeScreen.setCenterContent(multiPlayerOptions);
    }
    
    public void startSinglePlayerGame(String directoryPath) {
        GuiManager manager = new GuiManager(myStage);
        GuiConstants.GUI_MANAGER.init();
        manager.startSinglePlayerGame(directoryPath);
    }

    private ImageView getImageFromPath(String imagePath,double width,double height) {
        Image image = new Image(imagePath,width,height,false,true);
        return new ImageView(image);
    }
}
