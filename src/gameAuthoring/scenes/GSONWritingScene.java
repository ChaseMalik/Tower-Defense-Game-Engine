package gameAuthoring.scenes;

import gameAuthoring.mainclasses.AuthorController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class GSONWritingScene extends Scene {

    protected static final double MAX_LOADING_BAR_WIDTH = 390;
    private Rectangle myLoadingBar;
    private BorderPane myPane;

    public GSONWritingScene (BorderPane pane) {
        super(pane, AuthorController.SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT);
        myPane = pane;
        Label label = new Label("Your game is being created...");
        label.setStyle("-fx-font-size: 30px");
        myLoadingBar = new Rectangle(0, 0, 0, 2);
        VBox container = new VBox(60);
        container.setPadding(new Insets(200, 300, 0, 300));
        container.getChildren().addAll(label, myLoadingBar);
        pane.setCenter(container);
        activateLoadingBar();
    }

    private void activateLoadingBar () {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame loadingBarGrowth = new KeyFrame(Duration.seconds(.05),
                                                 new EventHandler<ActionEvent>() {
                                                     public void handle (ActionEvent event) {
                                                         myLoadingBar.setWidth(myLoadingBar
                                                                 .getWidth() + 3);
                                                         if (myLoadingBar.getWidth() > MAX_LOADING_BAR_WIDTH) {
                                                             timeline.stop();
                                                             ((VBox) myPane.getCenter())
                                                                     .getChildren()
                                                                     .remove(myLoadingBar);
                                                             ((VBox) myPane.getCenter())
                                                                     .getChildren()
                                                                     .add(new Label(
                                                                                    "Write completed!"));
                                                         }
                                                     }
                                                 });
        timeline.getKeyFrames().add(loadingBarGrowth);
        timeline.play();
    }
}
