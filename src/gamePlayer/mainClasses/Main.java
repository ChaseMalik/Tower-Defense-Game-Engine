package gamePlayer.mainClasses;

import gamePlayer.mainClasses.dummyGameManager.DummyGameManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) throws Exception {
        //builder will set up the application using the stage and given properties
        DummyGameManager manager = new DummyGameManager(stage);
        manager.run();
    }
}
