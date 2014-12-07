package gamePlayer.mainClasses.managers;

import gamePlayer.guiFeatures.LMController;
import gamePlayer.guiItems.welcome.LMConnector;
import gamePlayer.guiItems.welcome.WelcomeNavigator;
import gamePlayer.guiItemsListeners.LMConnectorListener;
import gamePlayer.guiItemsListeners.NavigatorListener;
import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.stage.Stage;

public class WelcomeManager implements LMConnectorListener, NavigatorListener {

	private static String guiBuilderPropertiesPath = "./src/gamePlayer/properties/welcome/WelcomeBuilderProperties.XML";
	
	private LMConnector leapConnector;
	private WelcomeNavigator navigator;

	private Stage myStage;

	public WelcomeManager(Stage stage) {
		myStage = stage;
		GuiConstants.WELCOME_MANAGER = this;
		LMController.getInstance().onConnect(event -> LMConnected());
	}

	public void init() {
		GuiBuilder.getInstance().build(myStage, guiBuilderPropertiesPath);
	}
	
	@Override
	public void registerLMConnector(LMConnector connector) {
		leapConnector = connector;
	}
	
	public void LMConnected() {
		leapConnector.deviceConnected(true);
		navigator.deviceConnected(true);
		LMController controller = LMController.getInstance();
		controller.onSwipeDown(event -> System.out.println("Swipe Down from Welcome Screen!"));
	}

	public void newGame() {
		LMController.getInstance().clearAllHandlers();
		GuiConstants.GUI_MANAGER.init();
	}

	@Override
	public void registerWelcomeNavigator(WelcomeNavigator navigator) {
		this.navigator = navigator;
	}

}
