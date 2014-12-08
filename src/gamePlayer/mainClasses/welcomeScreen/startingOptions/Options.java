package gamePlayer.mainClasses.welcomeScreen.startingOptions;

import gamePlayer.mainClasses.welcomeScreen.WelcomeScreen;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Options extends VBox {
    
    protected Button createOptionButton(ObjectProperty<String> objectProperty) {
        Button button = new Button();
        button.textProperty().bind(objectProperty);
        button.setPrefSize(WelcomeScreen.PANE_WIDTH,WelcomeScreen.PANE_HEIGHT/3);
        return button;
    }
}
