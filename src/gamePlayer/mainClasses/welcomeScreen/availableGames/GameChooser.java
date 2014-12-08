package gamePlayer.mainClasses.welcomeScreen.availableGames;

import gamePlayer.mainClasses.welcomeScreen.WelcomeScreen;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class GameChooser extends ScrollPane {
    private TilePane myPane;
    public static final double WIDTH = WelcomeScreen.CENTER_PANE_WIDTH;
    public static final double HEIGHT = WelcomeScreen.CENTER_PANE_HEIGHT;
    
    public GameChooser (String directory) {        
        myPane = new TilePane();
        myPane.setPrefSize(WIDTH,HEIGHT);
        myPane.getStyleClass().add("gameChooserTilePane");
        
        loadGameDescriptions(directory);
        this.setContent(myPane);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);    
    }
    
    private void loadGameDescriptions (String directory) {
        GameDescriptionLoader loader = new GameDescriptionLoader();      
        myPane.getChildren().addAll(loader.getDescriptions(directory));
    }
}
