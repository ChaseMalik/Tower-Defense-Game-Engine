package gamePlayer.mainClasses;

import gamePlayer.mainClasses.managers.GuiManager;
import gamePlayer.mainClasses.managers.WelcomeManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Class launches the application
 * @author allankiplagat, Brian Bolze
 *
 */
public class Main extends Application {
    
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) throws Exception {
        //builder will set up the application using the stage and given properties
    	
    	WelcomeManager welcomeManager = new WelcomeManager(stage);
    	GuiManager manager = new GuiManager(stage);
    	welcomeManager.init();
    	//manager.init();
    	/*
    	
        TestGameManager manager = new TestGameManager(stage);
        manager.run();*/
    }
}
