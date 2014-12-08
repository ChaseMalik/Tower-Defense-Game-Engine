package gamePlayer.mainClasses.welcomeScreen.availableGames;

import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import java.io.File;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class GameDescription extends Pane {
    public static final double WIDTH = GameChooser.WIDTH/3-10;
    public static final double HEIGHT = GameChooser.HEIGHT/2-10;
    
    public static final double GAME_IMAGE_HEIGHT =  3*HEIGHT/4;
    public static final double GAME_DESCRIPTION_HEIGHT = 2*HEIGHT/3;

    public GameDescription (ImageView image, File file) {
        this.setPrefSize(WIDTH, HEIGHT);
        this.setMinSize(WIDTH, HEIGHT);
        this.setMaxSize(WIDTH, HEIGHT);
        
        VBox box = new VBox();
        box.setPrefSize(WIDTH, HEIGHT);

        image.setFitWidth(WIDTH);
        image.setFitHeight(GAME_IMAGE_HEIGHT);
        image.getStyleClass().add("gameImage");
        image.setOnMouseReleased(event->GuiConstants.GAME_START_MANAGER.startGame(file));
        
        Label label = new Label(file.getName());
        label.getStyleClass().add("gameDescriptionLabel");
        label.setPrefSize(WIDTH, GAME_DESCRIPTION_HEIGHT);
        label.setTextAlignment(TextAlignment.CENTER);

        box.getChildren().addAll(image,label);
        this.getChildren().add(box);
    }
}
