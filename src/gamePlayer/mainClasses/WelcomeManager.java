package gamePlayer.mainClasses;

import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import javafx.scene.Group;
import javafx.stage.Stage;

public class WelcomeManager {
	
	// TODO Create this file
	private static final String guiBuilderPropertiesPath = "./src/gamePlayer/properties/welcome/WelcomeBuilderProperties.XML";

	private Stage myStage;
	private Group myRoot;
	
	public WelcomeManager(Stage stage) {
		myStage = stage;
	}
	
	public void init() {
		myRoot = GuiBuilder.getInstance().build(myStage, guiBuilderPropertiesPath);
	}

}
