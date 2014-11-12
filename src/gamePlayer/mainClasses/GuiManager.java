package gamePlayer.mainClasses;

import javafx.stage.Stage;

public class GuiManager {
    private Stage myStage;
    private static final String guiBuilderPropertiesPath = "./src/gamePlayer/properties/GuiBuilderProperties.XML";
    
    public GuiManager(Stage stage) {
        myStage = stage;
    }
    public void run () {
        GuiBuilder myBuilder = GuiBuilder.getInstance(guiBuilderPropertiesPath);
        myBuilder.build(myStage);
    }
}
