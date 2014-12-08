package gamePlayer.mainClasses.managers;

import gamePlayer.guiItems.welcome.WelcomeNavigator;
import gamePlayer.guiItemsListeners.NavigatorListener;
import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import gamePlayer.mainClasses.guiBuilder.GuiText;
import gamePlayer.mainClasses.welcomeScreen.LoadingIndicator;
import gamePlayer.mainClasses.welcomeScreen.availableGames.GameChooser;
import gamePlayer.mainClasses.welcomeScreen.startingOptions.MultiPlayerOptions;
import gamePlayer.mainClasses.welcomeScreen.startingOptions.PlayerCountOptions;

import java.io.File;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Dimension2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.XMLParsing.XMLParser;

public class WelcomeManager implements NavigatorListener {

	private static String guiBuilderPropertiesPath = "./src/gamePlayer/properties/welcome/WelcomeBuilderProperties.XML";
	private XMLParser myParser;
	private WelcomeNavigator navigator;
	private Stage myStage;
	private Group myGroup;
	private String gameTypeBeingChosen;

	public WelcomeManager(Stage stage) {
		myStage = stage;
		GuiConstants.WELCOME_MANAGER = this;
	}

	public void init() {
		GuiConstants.DYNAMIC_SIZING = false;
		myParser = new XMLParser(new File(guiBuilderPropertiesPath));
		myGroup = GuiBuilder.getInstance().build(myStage,
				guiBuilderPropertiesPath);
		addBackgroundImage();
		PlayerCountOptions playerCountOptions = new PlayerCountOptions();
        playerCountOptions.getSinglePlayerOption().setOnMouseReleased(event->startSinglePlayerGameChooser());
        playerCountOptions.getMultiPlayerOption().setOnMouseReleased(event->startMultiPlayerOptions());
        navigator.setContent(playerCountOptions); 
		GuiBuilder.getInstance().build(myStage, guiBuilderPropertiesPath);
	}

	public void newGame() {
		GuiConstants.GUI_MANAGER.init();
	}
	
	public void startGame(File file) {
		if (gameTypeBeingChosen.equals(GuiConstants.SINGLE_PLAYER_GAME)) {
			startSinglePlayerGame(file.getPath());
		} else if (gameTypeBeingChosen.equals(GuiConstants.MULTI_PLAYER_GAME)) {
			startMultiPlayerGame(file.getPath());
		}
	}

	@Override
	public void registerWelcomeNavigator(WelcomeNavigator navigator) {
		this.navigator = navigator;
	}

	public void joinMultiPlayerGame() {
		GuiManager manager = new GuiManager(myStage);
		manager.joinMultiPlayerGame();
	}

	private void startSinglePlayerGameChooser() {
		gameTypeBeingChosen = GuiConstants.SINGLE_PLAYER_GAME;
		GameChooser chooser = new GameChooser(
				GuiConstants.SINGLE_PLAYER_GAMES_DIRECTORY);
		navigator.setContent(chooser);
	}

	private void startMultiPlayerGameChooser() {
		gameTypeBeingChosen = GuiConstants.MULTI_PLAYER_GAME;
		GameChooser chooser = new GameChooser(
				GuiConstants.MULTI_PLAYER_GAMES_DIRECTORY);
		navigator.setContent(chooser);
	}

	private void startMultiPlayerOptions() {
		MultiPlayerOptions multiPlayerOptions = new MultiPlayerOptions();
		multiPlayerOptions.getNewGameOption().setOnMouseReleased(
				event -> startMultiPlayerGameChooser());
		multiPlayerOptions.getJoinGameOption().setOnMouseReleased(
				event -> joinMultiPlayerGame());
		navigator.setContent(multiPlayerOptions);
	}

	public void startSinglePlayerGame(String directoryPath) {
		GuiManager manager = new GuiManager(myStage);
		GuiConstants.GUI_MANAGER.init();
		manager.startSinglePlayerGame(directoryPath);
	}

	private void startMultiPlayerGame(String directoryPath) {
		// wait for other player to join
		navigator.setContent(new LoadingIndicator(
				GuiConstants.MULTILANGUAGE.getStringProperty(GuiText.WAITING_FOR_CHALLENGER)));

		GuiManager manager = new GuiManager(myStage);
		manager.prepareMultiPlayerGame(directoryPath);

		Timeline timeline = new Timeline();
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(1),
						event -> pollEngineForMultiPlayerReadiness(manager,
								timeline, directoryPath)));
		timeline.play();
	}

	private void pollEngineForMultiPlayerReadiness(GuiManager manager,
			Timeline timeline, String directoryPath) {
		if (manager.multiPlayerGameIsReady()) {
			timeline.stop();
			manager.startMultiPlayerGame();
		}
	}

	private ImageView getImageFromPath(String imagePath, double width,
			double height) {
		Image image = new Image(imagePath, width, height, false, true);
		return new ImageView(image);
	}
	
	private void addBackgroundImage() {
		Dimension2D windowSize = myParser.getDimension("GuiSize");
		ImageView image = getImageFromPath(
				myParser.getValuesFromTag("BackgroundImage").get(0),
				windowSize.getWidth(), windowSize.getHeight());
		myGroup.getChildren().add(image);
		image.toBack();
	}

}
