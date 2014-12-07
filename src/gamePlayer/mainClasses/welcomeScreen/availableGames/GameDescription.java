package gamePlayer.mainClasses.welcomeScreen.availableGames;

import gamePlayer.mainClasses.guiBuilder.GuiConstants;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GameDescription extends Pane {
    public static final double WIDTH = GameChooser.WIDTH;
    public static final double HEIGHT = GameChooser.HEIGHT/3;
    
    public static final double GAME_IMAGE_HEIGHT = 2*HEIGHT/3;
    public static final double GAME_DESCRIPTION_HEIGHT = 2*HEIGHT/3;
    
    private String directory;
    
    public GameDescription (ImageView image, String description,String gameDirectory) {
        directory = gameDirectory;
        
        this.setPrefSize(WIDTH, HEIGHT);
        this.setMaxSize(WIDTH, HEIGHT);
        
        VBox box = new VBox();
        box.setPrefSize(WIDTH, HEIGHT);
        box.setMaxSize(WIDTH, HEIGHT);
        
        image.setFitWidth(WIDTH);
        image.setFitHeight(GAME_IMAGE_HEIGHT);
        box.getChildren().add(image);
        
        Button button = new Button(description);
        button.getStyleClass().add("gameDescriptionButton");
        button.setPrefSize(WIDTH, GAME_DESCRIPTION_HEIGHT);
        button.setOnMouseReleased(event->GuiConstants.GAME_START_MANAGER.startSinglePlayerGame(gameDirectory));
        box.getChildren().add(button);
        
        this.getChildren().add(box);
    }
}
