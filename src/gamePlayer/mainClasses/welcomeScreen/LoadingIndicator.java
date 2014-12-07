package gamePlayer.mainClasses.welcomeScreen;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class LoadingIndicator extends Pane {
    private static final double WIDTH = WelcomeScreen.PANE_WIDTH;
    private static final double HEIGHT = WelcomeScreen.PANE_HEIGHT;
    
    private static final double INDICATOR_SIZE = 100;
    private static final double LABEL_HEIGHT = 40;
    
    public LoadingIndicator (String message) {
        ProgressIndicator indicator = new ProgressIndicator();
        indicator.setPrefSize(INDICATOR_SIZE, INDICATOR_SIZE);
        indicator.setLayoutX(WIDTH/2-INDICATOR_SIZE/2);
        indicator.setLayoutY(HEIGHT/2-INDICATOR_SIZE/2);
        indicator.setVisible(true);
        
        Label label = new Label(message);
        label.getStyleClass().add("OpponentWaitingLabel");
        label.setPrefSize(WIDTH, LABEL_HEIGHT);
        label.setAlignment(Pos.CENTER);
        label.setLayoutY(HEIGHT-LABEL_HEIGHT);
        
        FadeTransition ft = new FadeTransition(Duration.millis(1000), label);
        ft.setFromValue(1.0);
        ft.setToValue(0.2);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
        
        this.setPrefSize(WIDTH,HEIGHT);
        this.getChildren().addAll(indicator,label);
    }
}
