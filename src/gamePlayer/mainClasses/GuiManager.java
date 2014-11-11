package gamePlayer.mainClasses;

import javafx.stage.Stage;

public class GuiManager {
    private Stage myStage;
    public GuiManager(Stage stage) {
        myStage = stage;
    }
    public void run () {
        GuiBuilder myBuilder = GuiBuilder.getInstance();
        myBuilder.build(myStage);
    }
}
