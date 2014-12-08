package gamePlayer.mainClasses.welcomeScreen.availableGames;

import java.io.File;
import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameDescription extends Pane {
    public static final double WIDTH = GameChooser.WIDTH;
    public static final double HEIGHT = GameChooser.HEIGHT/2;
    
    public static final double GAME_IMAGE_HEIGHT =  2*HEIGHT/3;
    public static final double GAME_DESCRIPTION_HEIGHT = HEIGHT/3;

    public GameDescription (ImageView image, File file) {
        this.setPrefSize(WIDTH, HEIGHT);
        this.setMinSize(WIDTH, HEIGHT);
        
        VBox box = new VBox();
        box.setPrefSize(WIDTH, HEIGHT);
        
        image.setFitWidth(WIDTH);
        image.setFitHeight(GAME_IMAGE_HEIGHT);
        
        Button button = new Button(file.getName());
        button.getStyleClass().add("gameDescriptionButton");
        button.setPrefSize(WIDTH, GAME_DESCRIPTION_HEIGHT);
        button.setOnAction(event->GuiConstants.GAME_START_MANAGER.startGame(file));

        box.getChildren().addAll(image,button);
        this.getChildren().add(box);
    }
}
