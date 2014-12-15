package gamePlayer.mainClasses;

import gamePlayer.mainClasses.welcomeScreen.GameStartManager;
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
        new GameStartManager(stage);
    }
}
