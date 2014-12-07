package gamePlayer.mainClasses.welcomeScreen.startingOptions;

import gamePlayer.mainClasses.welcomeScreen.WelcomeScreen;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Options extends VBox {
    
    protected Button createOptionButton(String buttonText) {
        Button button = new Button();
        button.setText(buttonText);
        button.setPrefSize(WelcomeScreen.PANE_WIDTH,WelcomeScreen.PANE_HEIGHT/3);
        return button;
    }
}
