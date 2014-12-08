package gameAuthoring.scenes;

import gameAuthoring.mainclasses.AuthorController;
import gameAuthoring.mainclasses.Constants;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import utilities.multilanguage.MultiLanguageUtility;


public class GSONWritingScene extends Scene {

    private static final int SPACING = 60;
    private static final int BAR_HEIGHT = 2;
    private static final String WRITE_COMPLETED = "Write completed!";
    private static final int BAR_OFFSET = 3;
    protected static final double MAX_LOADING_BAR_WIDTH = 390;
    private Rectangle myLoadingBar;
    private BorderPane myPane;

    public GSONWritingScene (BorderPane pane) {
        super(pane, AuthorController.SCREEN_WIDTH, AuthorController.SCREEN_HEIGHT);
        myPane = pane;
        Label label = new Label();
        label.textProperty().bind(MultiLanguageUtility.getInstance()
                                  .getStringProperty(Constants.GAME_WRITING));
        label.setStyle("-fx-font-size: 30px");
        myLoadingBar = new Rectangle(0, 0, 0, BAR_HEIGHT);
        VBox container = new VBox(SPACING);
        container.setPadding(new Insets(200, 300, 0, 300));
        container.getChildren().addAll(label, myLoadingBar);
        pane.setCenter(container);
        activateLoadingBar();
    }

    private void activateLoadingBar () {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        KeyFrame loadingBarGrowth =
                new KeyFrame(Duration.seconds(.05),
                             event-> {
                                 myLoadingBar.setWidth(myLoadingBar
                                                       .getWidth() + BAR_OFFSET);
                                 if (myLoadingBar.getWidth() > MAX_LOADING_BAR_WIDTH) {
                                     timeline.stop();
                                     ((VBox) myPane.getCenter())
                                     .getChildren()
                                     .remove(myLoadingBar);
                                     ((VBox) myPane.getCenter())
                                     .getChildren()
                                     .add(new Label(WRITE_COMPLETED));
                                 }
                             });
        timeline.getKeyFrames().add(loadingBarGrowth);
        timeline.play();
    }
}
