package gamePlayer.mainClasses.welcomeScreen;

import javafx.scene.control.ProgressIndicator;

public class RotatingProgressIndicator extends ProgressIndicator {
    private static final double SIZE = 50;
    
    public RotatingProgressIndicator () {
        this.setPrefSize(SIZE, SIZE);
        this.setLayoutX(WelcomeScreen.PANE_WIDTH/2-SIZE/2);
        this.setLayoutY(WelcomeScreen.PANE_HEIGHT/2-SIZE/2);
        this.setVisible(true);
    }
}
