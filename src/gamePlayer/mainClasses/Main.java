package gamePlayer.mainClasses;

import gamePlayer.mainClasses.managers.GuiManager;
import gamePlayer.mainClasses.managers.WelcomeManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Class launches the application
 * @author allankiplagat, Brian Bolze
 */
public class Main extends Application {
    
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) throws Exception {
    	//GameStartManager manager = new GameStartManager(stage);
    	WelcomeManager manager = new WelcomeManager(stage);
    	new GuiManager(stage);
    	manager.init();
    }
}
