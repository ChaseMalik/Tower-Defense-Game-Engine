package gamePlayer.mainClasses;

import gamePlayer.mainClasses.managers.GuiManager;
import gamePlayer.mainClasses.managers.WelcomeManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class LM_Main extends Application {
    
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) throws Exception {
    	WelcomeManager welcomeManager = new WelcomeManager(stage);
    	new GuiManager(stage);
    	welcomeManager.init();
    }
}
