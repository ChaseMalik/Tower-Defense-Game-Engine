package gamePlayer.mainClasses.managers;

import gamePlayer.guiFeatures.LMConnectorListener;
import gamePlayer.guiFeatures.LeapMotionListener;
import gamePlayer.guiItems.welcome.LMConnector;
import gamePlayer.guiItems.welcome.WelcomeNavigator;
import gamePlayer.guiItemsListeners.NavigatorListener;
import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.stage.Stage;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Listener;

public class WelcomeManager implements LMConnectorListener, NavigatorListener {

	public static Controller LMController;
	public static Listener LMListener;
	private static String guiBuilderPropertiesPath = "./src/gamePlayer/properties/welcome/WelcomeBuilderProperties.XML";
	
	private LMConnector leapConnector;
	private WelcomeNavigator navigator;

	private Stage myStage;

	public WelcomeManager(Stage stage) {
		myStage = stage;
		GuiConstants.WELCOME_MANAGER = this;
	}

	public void init() {
		GuiBuilder.getInstance().build(myStage, guiBuilderPropertiesPath);
	}
	
	@Override
	public void registerLMConnector(LMConnector connector) {
		leapConnector = connector;
	}

	public void activateLeapMotion() {
		try {
			LMController = new Controller();
			LMListener = new LeapMotionListener();
			LMController.addListener(LMListener);
		} catch (Exception e) {
			System.out.println("Error connecting Leap Motion");
			return;
		}
		// TODO : Setup more LeapMotion stuff 
		// LMConnected();
	}
	
	public void LMConnected() {
		leapConnector.deviceConnected(true);
		navigator.deviceConnected(true);
	}

	public void newGame() {
		GuiConstants.GUI_MANAGER.init();
	}

	@Override
	public void registerWelcomeNavigator(WelcomeNavigator navigator) {
		this.navigator = navigator;
	}

}
