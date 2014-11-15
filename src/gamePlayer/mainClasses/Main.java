package gamePlayer.mainClasses;

import gamePlayer.mainClasses.dummyGameManager.DummyGameManager;
import gamePlayer.mainClasses.guiBuilder.GuiBuilder;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static final String guiBuilderPropertiesPath = "./src/gamePlayer/properties/GuiBuilderProperties.XML";
    
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start (Stage stage) throws Exception {
        GuiBuilder builder = GuiBuilder.getInstance(guiBuilderPropertiesPath);
        //builder will set up the application using the stage and given properties
        DummyGameManager manager = new DummyGameManager();
        builder.build(stage, new GuiManager(manager));
        
        manager.createGameStats();
        manager.updateGameStats();
    }
}
