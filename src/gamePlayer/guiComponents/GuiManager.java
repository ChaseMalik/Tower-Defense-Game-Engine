package gamePlayer.guiComponents;

import javafx.stage.Stage;

public class GuiManager {
    Stage myStage;
    public GuiManager(Stage stage) {
        myStage = stage;
    }
    public void run () {
        GuiBuilder myBuilder = GuiBuilder.getInstance();
    }
}
