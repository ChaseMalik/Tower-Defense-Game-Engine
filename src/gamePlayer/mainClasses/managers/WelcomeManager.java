package gamePlayer.mainClasses.managers;

import gamePlayer.guiItems.welcome.WelcomeNavigator;
import gamePlayer.guiItemsListeners.NavigatorListener;
import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.stage.Stage;


public class WelcomeManager implements NavigatorListener {

	private static String guiBuilderPropertiesPath = "./src/gamePlayer/properties/welcome/WelcomeBuilderProperties.XML";
	private WelcomeNavigator navigator;
	private Stage myStage;

	public WelcomeManager(Stage stage) {
		myStage = stage;
		GuiConstants.WELCOME_MANAGER = this;
	}

	public void init() {
		GuiConstants.DYNAMIC_SIZING = false;
		GuiBuilder.getInstance().build(myStage, guiBuilderPropertiesPath);
	}

	public void newGame() {
		GuiConstants.GUI_MANAGER.init();
	}

	@Override
	public void registerWelcomeNavigator(WelcomeNavigator navigator) {
		this.navigator = navigator;
	}

}
