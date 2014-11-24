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
	
	public void newGame() {
		GuiConstants.GUI_MANAGER.init();
	}

}
