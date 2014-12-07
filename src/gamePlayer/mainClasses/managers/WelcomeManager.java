package gamePlayer.mainClasses.managers;

import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.stage.Stage;

public class WelcomeManager {

	private static String guiBuilderPropertiesPath = "./src/gamePlayer/properties/welcome/WelcomeBuilderProperties.XML";

	private Stage myStage;

	public WelcomeManager(Stage stage) {
		myStage = stage;
		GuiConstants.WELCOME_MANAGER = this;
	}

	public void init() {
		GuiBuilder.getInstance().build(myStage, guiBuilderPropertiesPath);
	}
	
//	@Override
//	public void registerLMConnector(LMConnector connector) {
//		leapConnector = connector;
//	}
//
//	public void activateLeapMotion() {
//		try {
//			LMController = new Controller();
//			LMListener = new LeapMotionListener();
//			LMController.addListener(LMListener);
//		} catch (Exception e) {
//			System.out.println("Error connecting Leap Motion");
//			return;
//		}
//		// TODO : Setup more LeapMotion stuff 
//		// LMConnected();
//	}
//	
//	public void LMConnected() {
//		leapConnector.deviceConnected(true);
//		navigator.deviceConnected(true);
//	}

	public void newGame() {
		GuiConstants.GUI_MANAGER.init();
	}

//	@Override
//	public void registerWelcomeNavigator(WelcomeNavigator navigator) {
//		this.navigator = navigator;
//	}

}
