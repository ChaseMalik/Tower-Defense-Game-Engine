package gamePlayer.mainClasses.managers;

import gamePlayer.Listeners.LMConnectorListener;
import gamePlayer.Listeners.LeapMotionListener;
import gamePlayer.guiItems.welcome.LMConnector;
import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.stage.Stage;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Listener;

public class WelcomeManager implements LMConnectorListener {

	public static Controller LMController;
	public static Listener LMListener;
	private static String guiBuilderPropertiesPath = "./src/gamePlayer/properties/welcome/WelcomeBuilderProperties.XML";
	
	private LMConnector leapConnector;

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
		
	}
	
	public void LMConnected() {
		leapConnector.devicedConnected(true);
	}

	public void newGame() {
		GuiConstants.GUI_MANAGER.init();
	}

}
